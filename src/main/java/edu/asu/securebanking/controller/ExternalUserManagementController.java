package edu.asu.securebanking.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.net.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.securebanking.businessobject.CreateExternalAccountBO;
import edu.asu.securebanking.businessobject.PiiAuthBO;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.ExternalAccount;
import edu.asu.securebanking.model.InternalAccount;
import edu.asu.securebanking.model.InternalCriticalTransaction;
import edu.asu.securebanking.model.PiiAuth;

@Controller
@RequestMapping("/externalUserManagement")
public class ExternalUserManagementController {
	
	@Autowired
	CreateExternalAccountBO extBo;
	
	@Autowired
	PiiAuthBO piiAuthBo;

	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView initForm(ModelMap model, Principal principal) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		ModelAndView modelAndView = new ModelAndView("externalUserManagement");
		
		List<ExternalAccount> extpiireqs = extBo.getpiireq();
		if(extpiireqs != null)
		{
			modelAndView.addObject("extpiireqs", extpiireqs);
		
		
		for(int i=0; i<extpiireqs.size(); i++)
		{
			ExternalAccount temp = (ExternalAccount) extpiireqs.get(i);
			System.out.println("User SSN: "+ temp.getSsn());
			
			String passphrase = "HelloEncyption";
			MessageDigest digest = MessageDigest.getInstance("SHA");
			digest.update(passphrase.getBytes());
			SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
			
			byte[] ciphertext = Base64.decodeBase64(temp.getSsn().getBytes());		
			String cleartext = decrypt(ciphertext, key);			
			System.out.println("\nAfter Decryption: "+cleartext);
			
			temp.setSsn(cleartext);
			
		}
		}
		
		List<PiiAuth> piiAuths = piiAuthBo.getallPiiAuth();
		System.out.println("size of piiAuth list is: "+piiAuths.size());
		modelAndView.addObject("piiAuths", piiAuths);
		
		ExternalAccount user = new ExternalAccount();
		modelAndView.addObject("user", user);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/displayusers")
	public synchronized ModelAndView listOfUsers() {
		ModelAndView modelAndView = new ModelAndView("listExternalUsers");
		List<ExternalAccount> users = extBo.getdetails();
		System.out.println(users.listIterator());
		modelAndView.addObject("users", users);
		return modelAndView;
	}
	
	@RequestMapping(value="/finduser", method = RequestMethod.POST)
	public synchronized ModelAndView finduserbyid(@ModelAttribute("user") ExternalAccount user,
			BindingResult result) throws InternalException {
		ModelAndView modelAndView = new ModelAndView("findExternalUser");
		ExternalAccount userbyid = extBo.findUserByid(user.getUserid());
		if(userbyid == null){
			ModelAndView modelAndView1 = new ModelAndView("genericsuccess");
			modelAndView1.addObject("message", "User ID not found!");
			return modelAndView1;
		}
		System.out.println(userbyid.getUsername());
		modelAndView.addObject("userbyid", userbyid);
		return modelAndView;
	}
	
	@RequestMapping(value="/requestpii")
	public synchronized ModelAndView internalcriticaltransqueue(@ModelAttribute("userbyid") ExternalAccount userbyid,
			BindingResult result) throws InternalException{
				
		ModelAndView modelAndView = new ModelAndView("externalmgmtsuccessmsg");
		
		System.out.println("userid:"+userbyid.getUserid());
		
		PiiAuth piiAuth = new PiiAuth();
		piiAuth.setUserid(userbyid.getUserid());
		piiAuth.setIsauthorized("no");
		piiAuth.setAuthrequest("yes");
		
		System.out.println("entering pii auth!");
		
		piiAuthBo.save(piiAuth);

		System.out.println("PII Authorization request inserted!");
		String msg = "PII Authorization request inserted!";
		modelAndView.addObject("message", msg);
		return modelAndView;
	}
	
	@RequestMapping(value="/authorizepii")
	public synchronized ModelAndView authorizepii(@ModelAttribute("user") ExternalAccount user,
			BindingResult result) throws InternalException{
				
		ModelAndView modelAndView = new ModelAndView("externalmgmtsuccessmsg");
		
		System.out.println("userid:"+user.getUserid());
		
		
		
		System.out.println("entering pii auth!");
		
		piiAuthBo.authorize(user.getUserid(), "yes");

		System.out.println("PII request Authorizated!");
		String msg = "PII request Authorizated!";
		modelAndView.addObject("message", msg);
		return modelAndView;
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


}
