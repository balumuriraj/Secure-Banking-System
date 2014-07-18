package edu.asu.securebanking.controller;


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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
import edu.asu.securebanking.model.ExternalUserTransaction;
import edu.asu.securebanking.model.InternalAccount;
import edu.asu.securebanking.model.InternalUserTransaction;
import edu.asu.securebanking.validator.ExternalUserValidator;
import edu.asu.securebanking.validator.StringValidator;

@Controller
@SessionAttributes
public class CreateAccountController {

	@Autowired
	CreateInternalAccountBO internalAccountBO;

	@Autowired
	CreateExternalAccountBO externalAccountBO;

	@Autowired
	CreateInternalAccountBO intBo;


	@RequestMapping("/createExternal")
	public ModelAndView showCreateExternalAccount() {
		return new ModelAndView("createExternalAccount", "command", new ExternalAccount());
	}

	@RequestMapping("/createInternal")
	public ModelAndView showCreateInternalAccount() {
		return new ModelAndView("createInternalAccount", "command", new InternalAccount());
	}

	@RequestMapping(value = "/addExternalAccount", method = RequestMethod.POST)
	public synchronized ModelAndView addAccount(@ModelAttribute("ExternalAccount") ExternalAccount createAccount, BindingResult result) {

		//default values
		createAccount.setFirstTimeLogin(true);  //1 - true
		createAccount.setCurrentBalance(25.00);
		createAccount.setAuthorized(false);

		//change the logic here
		createAccount.setAccountNo("98765a");

		ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");

		CreateExternalAccountBO createExternalAccountBO = (CreateExternalAccountBO)appContext.getBean("createExternalAccountBO");

		createExternalAccountBO.save(createAccount);

		return new ModelAndView("externalAccountSuccess");
	}

	@RequestMapping(value = "/addInternalAccount", method = RequestMethod.POST)
	public synchronized ModelAndView addInternalAccount(@ModelAttribute("createInternalAccount") InternalAccount createInternalAccount, BindingResult result) {

		//default values
		createInternalAccount.setFirstTimeLogin(true);  //1 - true

		ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");

		CreateInternalAccountBO createInternalAccountBO = (CreateInternalAccountBO)appContext.getBean("createInternalAccountBO");

		createInternalAccountBO.save(createInternalAccount);

		return new ModelAndView("internalAccountSuccess");
	}

	// Internal User Account Functionalities.


	@RequestMapping(value="/internalUsersAccounts")
	public synchronized String internalUsersAccounts(ModelMap model) {

		InternalAccount internalAccount = new InternalAccount();

		//command object
		model.addAttribute("internalAccount", internalAccount);

		//return form view
		return "InternalUserAccount";
	}

	@RequestMapping(value="/displayInternalUsersAccounts")

	public synchronized ModelAndView listOfInternalUserAccounts() {
		ModelAndView modelAndView = new ModelAndView("listInternalUserAccounts");
		List<InternalAccount> internalAccounts = internalAccountBO.getAllInternalUserAccounts();
		InternalAccount singleInternalAccount = new InternalAccount();
		modelAndView.addObject("singleInternalAccount", singleInternalAccount);
		modelAndView.addObject("internalAccounts", internalAccounts);
		return modelAndView;
	}


	@RequestMapping(value="/viewInternalUserAccount")

	public synchronized ModelAndView viewInternalUserAccount(@ModelAttribute("accountbyid") InternalAccount internalAccount) {

		InternalAccount internalAccount1 = internalAccountBO.findUserByid(internalAccount.getEmployeeId());

		return new ModelAndView("viewInternalAccount","user",internalAccount1);
	}

	@RequestMapping(value="/updateInternalUserAccount")

	public ModelAndView showUpdateInternalUserAccount(@ModelAttribute("accountbyid") InternalAccount internalAccount) {

		InternalAccount internalAccount1 = internalAccountBO.findUserByid(internalAccount.getEmployeeId());

		return new ModelAndView("updateInternalUserAccount","user",internalAccount1);
	}





	@RequestMapping(value="/updateInternalUserAccountDetails")
	public ModelAndView updateInternalUserAccount(@ModelAttribute("user") InternalAccount internalAccount,
			BindingResult result) {

		ModelAndView modelAndView = new ModelAndView("internalUserAccountUpdated");

		internalAccountBO.update(internalAccount);

		modelAndView.addObject("message", internalAccount.getUsername());

		return modelAndView;

	}



	@RequestMapping(value="/findInternalAccount")
	public synchronized ModelAndView findAccountById(@ModelAttribute("internalAccount") InternalAccount account,
			BindingResult result) throws InternalException {
		ModelAndView modelAndView = new ModelAndView("findInternalAccount");
		InternalAccount accountbyid = internalAccountBO.findUserByid(account.getEmployeeId());
		modelAndView.addObject("accountbyid", accountbyid);
		return modelAndView;
	}

	// External User Account Functionalities.

	@RequestMapping(value="/externalUsersAccounts")
	public synchronized String externalUsersAccounts(ModelMap model, Principal principal) {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		model.addAttribute("currentuser", currentuser);

		if(currentuser.getDeptid() == 3){
			ExternalAccount externalAccount = new ExternalAccount();

			//command object
			model.addAttribute("externalAccount", externalAccount);

			//return form view
			return "ExternalUserAccount";
		} else{
			String accessdenied = "Access Denied";
			model.addAttribute("accessdenied", accessdenied);

			return "accessdenied";
		}
	}

	@RequestMapping(value="/displayExternalUsersAccounts")

	public synchronized ModelAndView listOfExternalUserAccounts( Principal principal) {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 3){
			ModelAndView modelAndView = new ModelAndView("ListExternalUserAccounts");
			List<ExternalAccount> externalAccounts = externalAccountBO.getAllExternalUserAccounts();
			ExternalAccount accountbyname = new ExternalAccount();
			modelAndView.addObject("accountbyname", accountbyname);
			modelAndView.addObject("externalAccounts", externalAccounts);
			modelAndView.addObject("currentuser", currentuser);
			return modelAndView;
		} else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}
	}


	@RequestMapping(value="/viewExternalUserAccount")

	public ModelAndView viewExternalUserAccount(@ModelAttribute("accountbyname") ExternalAccount externalAccount, Principal principal) {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 3){

			ExternalAccount externalAccount1 = externalAccountBO.findAccountByName(externalAccount.getUsername());
			ModelAndView modelAndView = new ModelAndView("ViewExternalAccount");

			modelAndView.addObject("currentuser", currentuser);
			modelAndView.addObject("user", externalAccount1);

			return modelAndView;

		} else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}
	}

	@RequestMapping(value="/updateExternalUserAccount")

	public synchronized ModelAndView showUpdateExternalUserAccount(@ModelAttribute("accountbyname") ExternalAccount externalAccount, Principal principal) {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 3){


			ExternalAccount externalAccount1 = externalAccountBO.findAccountByName(externalAccount.getUsername());
			ModelAndView modelAndView = new ModelAndView("UpdateExternalUserAccount");

			modelAndView.addObject("currentuser", currentuser);
			modelAndView.addObject("user", externalAccount1);

			return modelAndView;
		} else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}
	}





	@RequestMapping(value="/updateExternalUserAccountDetails")
	public synchronized ModelAndView updateExternalUserAccount(@ModelAttribute("user") ExternalAccount externalAccount,
			BindingResult result, Principal principal) {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);
		if(currentuser.getDeptid() == 3){
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

			res = StringValidator.inputvalidation(externalAccount.getTelephone(), "number");
			if(!res){
				errors = errors + "Please enter valid phone number;";
			}

			res = StringValidator.inputvalidation(externalAccount.getEmail(), "email");
			if(!res){
				errors = errors + "Please enter valid email;";
			}


			if (errors != "") {
				//if validator failed
				ModelAndView modelAndView = new ModelAndView("UpdateExternalUserAccount");
				modelAndView.addObject("currentuser", currentuser);
				modelAndView.addObject("user", externalAccount);
				modelAndView.addObject("errors", errors);
				return modelAndView;
			} else{
				//System.out.println(externalAccount.getAccountNo()+externalAccount.getAddress()+externalAccount.getCurrentBalance()+externalAccount.getType());
				externalAccountBO.update(externalAccount);
				ModelAndView modelAndView = new ModelAndView("ExternalUserAccountUpdated");
				modelAndView.addObject("message", externalAccount.getUsername());
				return modelAndView;
			}
		} else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}

	}



	@RequestMapping(value="/findExternalAccount")
	public synchronized ModelAndView findAccountById(@ModelAttribute("externalAccount") ExternalAccount account,
			BindingResult result) throws InternalException {
		ModelAndView modelAndView = new ModelAndView("FindExternalAccount");
		ExternalAccount accountbyname = externalAccountBO.findAccountByName(account.getUsername());

		modelAndView.addObject("accountbyname", accountbyname);
		return modelAndView;
	}


}
