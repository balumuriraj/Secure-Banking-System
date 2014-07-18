package edu.asu.securebanking.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;

import org.apache.commons.net.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.securebanking.businessobject.CreateExternalAccountBO;
import edu.asu.securebanking.businessobject.PassKeyBO;
import edu.asu.securebanking.model.ExternalAccount;
import edu.asu.securebanking.validator.ExternalUserValidator;

@Controller
@RequestMapping("/ExternalRegistration")
public class ExternalRegistrationController {
	
	@Autowired
	ExternalUserValidator userValidator;
	
	@Autowired
	CreateExternalAccountBO extBo;
	
	@Autowired
	PassKeyBO passKeyBO;
	
	@InitBinder  
    private void initBinder(WebDataBinder binder) {  
        binder.setValidator(userValidator);  
    }
	
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model, HttpSession session) {
		
		if(session.getAttribute("passkeycheck") == "true"){
			ExternalAccount user = new ExternalAccount();
			
			//command object
			model.addAttribute("user", user);
	 
			//return form view
			return "createExternalAccount";
		}
		else{
			model.addAttribute("accessdenied", "You can't access this page directly. Use the passkey to register!");
			return "accessdenied";
		}
 
		
	}
 
	@RequestMapping(method = RequestMethod.POST)
	public synchronized ModelAndView processSubmit(
		@Validated @ModelAttribute("user") ExternalAccount user,
		BindingResult result, SessionStatus status, HttpSession session) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		
		if (result.hasErrors()) {
			//if validator failed
			ModelAndView modelAndView = new ModelAndView("createExternalAccount");
			return modelAndView;
		} else if(user.isCheckbox() != true){
			ModelAndView modelAndView = new ModelAndView("createExternalAccount");
			modelAndView.addObject("checkbox", "Please Accept Terms and conditions to proceed");
			return modelAndView;
		}
		else {
			
			String passphrase = "HelloEncyption";
			MessageDigest digest = MessageDigest.getInstance("SHA");
			digest.update(passphrase.getBytes());
			SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
			
			String unencrypteddata = user.getSsn();
			byte[] ciphertext = encrypt(unencrypteddata, key);			
			String cleartext = decrypt(ciphertext, key);			
			System.out.println("\nAfter Decryption: "+cleartext);
			
			String ssn = new String(Base64.encodeBase64(ciphertext));
			user.setSsn(ssn);
			
			long AccountNo = (long) Math.floor(Math.random() * 900000000000000L) + 100000000000000L;
			
			String password = getHash(user.getPassword());
			user.setPassword(password);		
			
			String vpassword = getHash(user.getVpassword());
			user.setVpassword(vpassword);
			
			user.setAuthorized(false);
			user.setType("individual");
			user.setFirstTimeLogin(true);
			user.setCurrentBalance(25.00);
			user.setAccountNo(Long.toString(AccountNo));
			System.out.println(AccountNo);
			
			String errors = extBo.save(user);
			if(errors == ""){
				if(session.getAttribute("passkey")!= null)
				{
					passKeyBO.delete(session.getAttribute("passkey").toString());
				}
				ModelAndView modelAndView = new ModelAndView("redirect:/ExternalRegistration/Success");
				
				status.setComplete();
				return modelAndView;
			} else{
				System.out.println("errors!");
				ModelAndView modelAndView = new ModelAndView("createExternalAccount");
				modelAndView.addObject("errors", errors);
				return modelAndView;
			}

		}
	}
	
	@ModelAttribute("securityList")
	public List<String> populateSecurityList() {
 
		//Data referencing 
		List<String> securityList = new ArrayList<String>();
		securityList.add("What is your favourite color?");
		securityList.add("What is your favourite movie");
		securityList.add("What is your favourite place?");
		securityList.add("What is your favourite sport?");
		securityList.add("What is your favourite phone?");
 
		return securityList;
	}
	
	public String getHash(String password) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
 
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
		return sb.toString();	
	}
	
	public byte[] encrypt(String data, SecretKeySpec key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException{
		Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
		aes.init(Cipher.ENCRYPT_MODE, key);
		byte[] ciphertext = aes.doFinal(data.getBytes());
		return ciphertext;
	}
	
	public String decrypt(byte[] ciphertext, SecretKeySpec key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
		aes.init(Cipher.DECRYPT_MODE, key);
		String cleartext = new String(aes.doFinal(ciphertext));
		return cleartext;
	}
	
/*	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
*/
}
