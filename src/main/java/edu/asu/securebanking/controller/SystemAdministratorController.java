package edu.asu.securebanking.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.securebanking.businessobject.CreateExternalAccountBO;
import edu.asu.securebanking.businessobject.CreateInternalAccountBO;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.ExternalAccount;
import edu.asu.securebanking.model.InternalAccount;
import edu.asu.securebanking.model.InternalUserTransaction;
import edu.asu.securebanking.validator.StringValidator;


@Controller
@SessionAttributes
@RequestMapping("/SystemAdmin")
public class SystemAdministratorController {

	@Autowired
	CreateInternalAccountBO internalAccountBO;

	@Autowired
	CreateExternalAccountBO externalAccountBO;

	/*@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model){

		//return form view
		return "SystemAdminHomePage";
	}*/
	
	@RequestMapping(value="/addexternalaccount")
	public ModelAndView addexternalaccount(HttpSession session) {
		session.setAttribute("passkeycheck", "true");
		ModelAndView modelAndView = new ModelAndView("redirect");
		return modelAndView;

	}
	
	@RequestMapping(value="/addinternalaccount")
	public ModelAndView addinternalaccount(HttpSession session) {
		session.setAttribute("passkeycheck", "true");
		ModelAndView modelAndView = new ModelAndView("redirectinternal");
		return modelAndView;

	}


	@RequestMapping(value="/internalUsersNewRequests")
	public synchronized ModelAndView internalUsers(ModelMap model) {

		ModelAndView modelAndView = new ModelAndView("SystemAdminListInternalUserRequests");
		List<InternalAccount> internalAccounts = internalAccountBO.getAllInternalUserAccounts();
		modelAndView.addObject("internalAccounts", internalAccounts);
		InternalAccount singleinternalAccount = new InternalAccount();
		modelAndView.addObject("singleInternalAccount", singleinternalAccount);
		return modelAndView;

	}

	@RequestMapping(value="/authorizeUserRequest")
	public synchronized ModelAndView authorizeUserRequest(@ModelAttribute("singleInternalAccount") InternalAccount internalUserAccount,
			BindingResult result) {

		ModelAndView modelAndView = new ModelAndView("InternalUserAuthorized");
		InternalAccount internalAccount = internalAccountBO.findUserByid(internalUserAccount.getEmployeeId());
		internalAccount.setAuthorized(true);
		internalAccountBO.update(internalAccount);
		return modelAndView;

	}

	@RequestMapping(value="/assignRoleInternalUserAccount")

	public synchronized ModelAndView assignroleInternalUserAccount(@ModelAttribute("singleInternalAccount") InternalAccount intuser) {

		InternalAccount internalAccount = internalAccountBO.findUserByid(intuser.getEmployeeId());

		return new ModelAndView("AssignRoleInternalUserAccount","user",internalAccount);
	}

	@RequestMapping(value="/internalUserRoleAssigned")

	public synchronized ModelAndView assignRoleInternalUserAccount(@ModelAttribute("user") InternalAccount internalUserAccount,BindingResult result) {

		ModelAndView modelAndView = new ModelAndView("genericsuccess");

		internalAccountBO.update(internalUserAccount);

		modelAndView.addObject("message", "Role updated for account with username - "+internalUserAccount.getUsername());

		return modelAndView;
	}


	@RequestMapping(value="/externalUsersNewRequests")
	public synchronized ModelAndView externalUsers(ModelMap model) {

		ModelAndView modelAndView = new ModelAndView("SystemAdminListExternalUserRequests");
		List<ExternalAccount> externalAccounts = externalAccountBO.getAllExternalUserAccounts();
		ExternalAccount singleExternalAccount = new ExternalAccount();
		modelAndView.addObject("singleExternalAccount", singleExternalAccount);
		modelAndView.addObject("externalAccounts", externalAccounts);
		return modelAndView;

	}

	@RequestMapping(value="/authorizeExtUserRequest")
	public synchronized ModelAndView authorizeUserRequest(@ModelAttribute("singleExternalAccount") ExternalAccount externalUserAccount,
			BindingResult result) {

		ModelAndView modelAndView = new ModelAndView("genericsuccess");
		ExternalAccount externalAccount = externalAccountBO.findAccountByName(externalUserAccount.getUsername());
		externalAccount.setAuthorized(true);
		externalAccountBO.update(externalAccount);
		modelAndView.addObject("message", "Customer Account Activated!");
		return modelAndView;

	}

	@RequestMapping(value="/assignTypeExternalUserAccount")

	public synchronized ModelAndView showUpdateExternalUserAccount(@ModelAttribute("singleExternalAccount") ExternalAccount externalUserAccount,BindingResult result) {

		ExternalAccount externalAccount = externalAccountBO.findAccountByName(externalUserAccount.getUsername());

		return new ModelAndView("AssignTypeExternalUserAccount","user",externalAccount);
	}

	@RequestMapping(value="/externalUserTypeAssigned")
	public synchronized ModelAndView updateExternalUserAccount(@ModelAttribute("user") ExternalAccount externalAccount,
			BindingResult result) {

		ModelAndView modelAndView = new ModelAndView("genericsuccess");

		externalAccountBO.update(externalAccount);

		modelAndView.addObject("message", "Type assigned for customer with username - "+externalAccount.getUsername());

		return modelAndView;

	}




	// External User Account Functionalities.


	@RequestMapping("/SystemAdminCreateExternal")
	public synchronized ModelAndView showCreateExternalAccount() {
		return new ModelAndView("SystemAdminCreateExternalAccount", "command", new ExternalAccount());
	}


	@RequestMapping(value="/SystemAdminAddExternalUsersAccount")

	public synchronized ModelAndView addExternalUserAccount(@ModelAttribute("command") ExternalAccount externalAccount,
			BindingResult result)throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

		String passphrase = "HelloEncyption";
		MessageDigest digest = MessageDigest.getInstance("SHA");
		digest.update(passphrase.getBytes()); 
		SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");

		String unencrypteddata = externalAccount.getSsn();
		byte[] ciphertext = encrypt(unencrypteddata, key);			
		String cleartext = decrypt(ciphertext, key);			
		System.out.println("\nAfter Decryption: "+cleartext);

		String ssn = new String(Base64.encodeBase64(ciphertext));
		externalAccount.setSsn(ssn);

		String password = getHash(externalAccount.getPassword());
		externalAccount.setPassword(password);		

		String vpassword = getHash(externalAccount.getVpassword());
		externalAccount.setVpassword(vpassword);

		//default values
		externalAccount.setFirstTimeLogin(true);  //1 - true

		externalAccount.setCurrentBalance(25.00);

		externalAccount.setSecurityquestion("test");

		externalAccount.setAuthorized(false);

		externalAccount.setType("individual");

		//change the logic here
		externalAccount.setAccountNo("125125125");

		externalAccountBO.save(externalAccount);


		return new ModelAndView("SystemAdminExternalAccountSuccess");
	}

	@RequestMapping(value="/externalUsersAccounts")
	public synchronized String externalUsersAccounts(ModelMap model) {

		ExternalAccount externalAccount = new ExternalAccount();

		//command object
		model.addAttribute("externalAccount", externalAccount);

		//return form view
		return "SystemAdminExternalUserAccount";
	}

	@RequestMapping(value="/displayExternalUsersAccounts")

	public synchronized ModelAndView listOfExternalUserAccounts() {
		ModelAndView modelAndView = new ModelAndView("SystemAdminExternalUserAccounts");
		List<ExternalAccount> externalAccounts = externalAccountBO.getAllExternalUserAccounts();
		ExternalAccount singleExternalAccount = new ExternalAccount();

		modelAndView.addObject("singleExternalAccount", singleExternalAccount);	
		modelAndView.addObject("externalAccounts", externalAccounts);
		return modelAndView;
	}


	@RequestMapping(value="/viewExternalUserAccount")

	public synchronized ModelAndView viewExternalUserAccount(@ModelAttribute("singleExternalAccount") ExternalAccount externalAccount) {

		ExternalAccount externalAccount1 = externalAccountBO.findAccountByName(externalAccount.getUsername());

		return new ModelAndView("SystemAdminViewExternalAccount","user",externalAccount1);
	}

	@RequestMapping(value="/updateExternalUserAccount")

	public synchronized ModelAndView showUpdateExternalUserAccount(@ModelAttribute("singleExternalAccount") ExternalAccount externalAccount) {

		ExternalAccount externalAccount1 = externalAccountBO.findAccountByName(externalAccount.getUsername());

		return new ModelAndView("SystemAdminUpdateExternalUserAccount","user",externalAccount1);
	}



	@RequestMapping(value="/updateExternalUserAccountDetails")

	public synchronized ModelAndView updateExternalUserAccountDetails(@ModelAttribute("user") ExternalAccount externalAccount,
			BindingResult result) {
		boolean res;
		String errors = "";

		res = StringValidator.inputvalidation(externalAccount.getUsername(), "username");
		if(!res){
			errors = errors + "Please enter valid username;";
		}

		res = StringValidator.inputvalidation(externalAccount.getFirstname(), "general");
		if(!res){
			errors = errors + "Please enter valid Firstname;";
		}

		res = StringValidator.inputvalidation(externalAccount.getLastname(), "general");
		if(!res){
			errors = errors + "Please enter valid Lastname;";
		}

		res = StringValidator.inputvalidation(externalAccount.getDob(), "date");
		if(!res){
			errors = errors + "Please enter valid Date of birth;";
		}

		res = StringValidator.inputvalidation(externalAccount.getAddress(), "address");
		if(!res){
			errors = errors + "Please enter valid Address;";
		}


		if(errors != "")
		{
			ModelAndView modelAndView = new ModelAndView("SystemAdminUpdateExternalUserAccount");
			modelAndView.addObject("singleExternalAccount", externalAccount);
			modelAndView.addObject("errors", errors);
			return modelAndView;

		} else{

		ModelAndView modelAndView = new ModelAndView("genericsuccess");

		externalAccountBO.update(externalAccount);

		modelAndView.addObject("message", "Updated Customer Account with username - "+externalAccount.getUsername());

		return modelAndView;
		}

	}



	@RequestMapping(value="/findExternalAccount")
	public synchronized ModelAndView findExternalAccount(@ModelAttribute("externalAccount") ExternalAccount account) throws InternalException {
		ModelAndView modelAndView = new ModelAndView("SystemAdminExternalAccount");
		ExternalAccount accountbyname = externalAccountBO.findAccountByName(account.getUsername());
		if(accountbyname==null)
		{
			return new ModelAndView("SystemAdminUsernameNameNotFound");
		}
		modelAndView.addObject("accountbyname", accountbyname);
		return modelAndView;
	}

	@RequestMapping(value="/findExternalRequest")
	public synchronized ModelAndView findExternalReques(@ModelAttribute("externalAccount") ExternalAccount account) throws InternalException {
		ModelAndView modelAndView = new ModelAndView("SystemAdminExternalRequest");
		ExternalAccount accountbyname = externalAccountBO.findAccountByName(account.getUsername());
		if(accountbyname==null)
		{
			return new ModelAndView("SystemAdminUsernameNameNotFound");
		}
		modelAndView.addObject("accountbyname", accountbyname);
		return modelAndView;
	}


	@RequestMapping(value="/deleteExternalUserAccount")
	public synchronized ModelAndView deleteExternalAccount(@ModelAttribute("accountbyname") ExternalAccount externalAccount) throws InternalException {
		ModelAndView modelAndView = new ModelAndView("SystemAdminExternalAccountDeleted");
		ExternalAccount accountbyname = externalAccountBO.findAccountByName(externalAccount.getUsername());
		externalAccountBO.delete(accountbyname);
		return modelAndView;
	}

	// Internal User Account Functionalities.


	@RequestMapping(value="/internalUsersAccounts")
	public synchronized String internalUsersAccounts(ModelMap model) {

		InternalAccount internalAccount = new InternalAccount();

		//command object
		model.addAttribute("internalAccount", internalAccount);

		//return form view
		return "SystemAdminInternalUserAccount";
	}

	@RequestMapping(value="/findInternalAccount")
	public synchronized ModelAndView findAccountById(@ModelAttribute("internalAccount") InternalAccount account,
			BindingResult result) throws InternalException {
		ModelAndView modelAndView = new ModelAndView("SystemAdminInternalAccount");
		InternalAccount accountbyid = internalAccountBO.findUserByid(account.getEmployeeId());
		modelAndView.addObject("accountbyid", accountbyid);
		return modelAndView;
	}

	@RequestMapping(value="/findInternalRequest")
	public synchronized ModelAndView findInternalRequest(@ModelAttribute("internalAccount") InternalAccount account,
			BindingResult result) throws InternalException {
		ModelAndView modelAndView = new ModelAndView("SystemAdminInternalRequest");
		InternalAccount accountbyid = internalAccountBO.findUserByid(account.getEmployeeId());
		modelAndView.addObject("accountbyid", accountbyid);
		return modelAndView;
	}

	@RequestMapping("/SystemAdminCreateInternal")
	public synchronized ModelAndView showCreateInternalAccount() {
		return new ModelAndView("SystemAdminCreateInternalAccount", "command", new InternalAccount());
	}


	@RequestMapping(value="/SystemAdminAddInternalUsersAccount")

	public synchronized ModelAndView addInternalUserAccount(@ModelAttribute("command") InternalAccount internalAccount,
			BindingResult result) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

		String passphrase = "HelloEncyption";
		MessageDigest digest = MessageDigest.getInstance("SHA");
		digest.update(passphrase.getBytes());
		SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");

		String unencrypteddata = internalAccount.getSsn();
		byte[] ciphertext = encrypt(unencrypteddata, key);			
		String cleartext = decrypt(ciphertext, key);			
		System.out.println("\nAfter Decryption: "+cleartext);

		String ssn = new String(Base64.encodeBase64(ciphertext));
		internalAccount.setSsn(ssn);

		String password = getHash(internalAccount.getPassword());
		internalAccount.setPassword(password);		

		String vpassword = getHash(internalAccount.getVpassword());
		internalAccount.setVpassword(vpassword);


		//default values
		internalAccount.setFirstTimeLogin(true);  //1 - true

		internalAccount.setSecurityquestion("test");

		internalAccount.setAuthorized(true);


		internalAccountBO.save(internalAccount);


		return new ModelAndView("SystemAdminInternalAccountSuccess");
	}

	@RequestMapping(value="/displayInternalUsersAccounts")

	public synchronized ModelAndView listOfInternalUserAccounts() {
		ModelAndView modelAndView = new ModelAndView("SystemAdminInternalUserAccounts");
		List<InternalAccount> internalAccounts = internalAccountBO.getAllInternalUserAccounts();
		InternalAccount singleInternalAccount = new InternalAccount();
		modelAndView.addObject("singleInternalAccount", singleInternalAccount);
		modelAndView.addObject("internalAccounts", internalAccounts);
		return modelAndView;
	}

	@RequestMapping(value="/viewInternalUserAccount")

	public synchronized ModelAndView viewInternalUserAccount(@ModelAttribute("singleInternalAccount") InternalAccount internalAccount) {

		InternalAccount internalAccount1 = internalAccountBO.findUserByid(internalAccount.getEmployeeId());

		return new ModelAndView("SystemAdminViewInternalAccount","user",internalAccount1);
	}

	@RequestMapping(value="/updateInternalUserAccount")

	public synchronized ModelAndView showUpdateInternalUserAccount(@ModelAttribute("singleInternalAccount") InternalAccount internalAccount) {

		InternalAccount internalAccount1 = internalAccountBO.findUserByid(internalAccount.getEmployeeId());

		return new ModelAndView("SystemAdminUpdateInternalUserAccount","user",internalAccount1);
	}



	@RequestMapping(value="/updateInternalUserAccountDetails")

	public synchronized ModelAndView updateInternalUserAccountDetails(@ModelAttribute("user") InternalAccount internalAccount,BindingResult result) {

		boolean res;
		String errors = "";

		res = StringValidator.inputvalidation(internalAccount.getUsername(), "username");
		if(!res){
			errors = errors + "Please enter valid username;";
		}

		res = StringValidator.inputvalidation(internalAccount.getFirstname(), "general");
		if(!res){
			errors = errors + "Please enter valid Firstname;";
		}

		res = StringValidator.inputvalidation(internalAccount.getLastname(), "general");
		if(!res){
			errors = errors + "Please enter valid Lastname;";
		}

		res = StringValidator.inputvalidation(internalAccount.getDob(), "date");
		if(!res){
			errors = errors + "Please enter valid Date of birth;";
		}

		res = StringValidator.inputvalidation(internalAccount.getAddress(), "address");
		if(!res){
			errors = errors + "Please enter valid Address;";
		}


		if(errors != "")
		{
			ModelAndView modelAndView = new ModelAndView("SystemAdminUpdateInternalUserAccount");
			modelAndView.addObject("singleInternalAccount", internalAccount);
			modelAndView.addObject("errors", errors);
			return modelAndView;

		} else{
		ModelAndView modelAndView = new ModelAndView("genericsuccess");

		internalAccountBO.update(internalAccount);

		modelAndView.addObject("message", "Updated Employee Account with username - "+internalAccount.getUsername());

		return modelAndView;
		}

	}

	@RequestMapping(value="/deleteInternalUserAccount")
	public synchronized ModelAndView deleteInternalAccount(@ModelAttribute("accountbyid") InternalAccount internalAccount) throws InternalException {
		ModelAndView modelAndView = new ModelAndView("SystemAdminInternalAccountDeleted");
		InternalAccount accountbyname = internalAccountBO.findUserByusername(internalAccount.getUsername());
		internalAccountBO.deleteUser(accountbyname);
		return modelAndView;
	}

	public synchronized String getHash(String password) throws NoSuchAlgorithmException{
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




	@RequestMapping(value="/viewLogs")
	public ModelAndView viewLogFiles() {

		ModelAndView modelAndView = new ModelAndView("SystemAdminViewLog");
		return modelAndView;

	}

}
