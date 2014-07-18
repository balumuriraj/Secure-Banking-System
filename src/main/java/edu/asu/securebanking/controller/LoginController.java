package edu.asu.securebanking.controller;

import java.security.MessageDigest;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.securebanking.businessobject.CreateExternalAccountBO;
import edu.asu.securebanking.businessobject.CreateInternalAccountBO;
import edu.asu.securebanking.businessobject.LoginAttemptBO;
import edu.asu.securebanking.businessobject.OTPBO;


import edu.asu.securebanking.model.ExternalAccount;
import edu.asu.securebanking.model.InternalAccount;
import edu.asu.securebanking.model.InternalUserTransaction;
import edu.asu.securebanking.model.OTP;
import edu.asu.securebanking.validator.StringValidator;


@Controller
@SessionAttributes
public class LoginController {
	
	@Autowired

	LoginAttemptBO loginAttemptBO;
	
    @Autowired
	CreateInternalAccountBO internalAccountBO;
	
	@Autowired
	CreateExternalAccountBO externalAccountBO;
	

	@RequestMapping(value="/welcome", method = RequestMethod.GET)
	public synchronized String printWelcome(ModelMap model, Principal principal ) {

 
		String name = principal.getName();

		ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
		LoginAttemptBO loginAttemptBO = (LoginAttemptBO)appContext.getBean("loginAttemptBO");
		OTPBO otpBO = (OTPBO)appContext.getBean("OTPBO");

		


		int userid = 0;
		int empid = 0;
		String email = null;
		String username = name;
		boolean ftl = false;
		InternalAccount ia=null;
		ExternalAccount ea = loginAttemptBO.findAccountByNameExternal(name);
		if(ea == null){
			ia = loginAttemptBO.findAccountByNameInternal(name);
			if(ia != null){
				userid = 0;
				empid = ia.getEmployeeId();
				email = ia.getEmail();
				ftl = ia.getFirstTimeLogin();
			}
		}else{
			//external use case
			userid = ea.getUserid();
			empid = 0;
			email = ea.getEmail();
			ftl = ea.isFirstTimeLogin();
		}
		if(ftl == true){
			// First Time Login
			//store OTP data to otp DB
			OTP instance = new OTP();
			instance.setuserid(userid);
			instance.setempid(empid);
			String otp_pwd = generateOTP();
			instance.setotp(otp_pwd);
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp param = new java.sql.Timestamp(date.getTime());
			instance.settime(param);
			//automatically check if we have matching userid & empid (logic is in OTPDaoImpl.java)
			otpBO.save(instance);
			
			this.sendEmail(email, username, instance.getotp());
			
			 return "OPTCreationSucess";
             
		}else{
			if(ea != null)
			{
			model.addAttribute("currentuser", ea);
			}
			else
			{
				model.addAttribute("currentuser", ia);
			}
				
             return "welcomepage";   
			
		}
		
	}

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public synchronized ModelAndView login(Principal principal) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		if (!(auth instanceof AnonymousAuthenticationToken)) { 
			ModelAndView modelAndView = new ModelAndView("welcomepage");
			String name = principal.getName();
			InternalAccount currentuser = internalAccountBO.findUserByusername(name);
			modelAndView.addObject("currentuser", currentuser);
			return modelAndView;
		} else{
			ModelAndView modelAndView = new ModelAndView("login");
			return modelAndView;
		}

	}
 
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
 
		model.addAttribute("message", "Login Failed or Account not yet activated by System Admin!");
		return "login";
 
	}
	@RequestMapping(value="/ForgetPwd", method = RequestMethod.GET)
	public ModelAndView forgetPwd(ModelMap model) {
		 InternalAccount forgot_user = new InternalAccount();
		 ModelAndView modelAndView = new ModelAndView("ForgetPassword");
		 modelAndView.addObject("forgot_user", forgot_user);
			return modelAndView;
 
	}
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public ModelAndView logout(Principal principal) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		if (!(auth instanceof AnonymousAuthenticationToken)) { 
			ModelAndView modelAndView = new ModelAndView("welcomepage");
			String name = principal.getName();
			InternalAccount currentuser = internalAccountBO.findUserByusername(name);
			modelAndView.addObject("currentuser", currentuser);
			return modelAndView;
		} else{
			ModelAndView modelAndView = new ModelAndView("login");
			return modelAndView;
		}
		
	}

	
	
	@RequestMapping(value="/FirstTime", method = RequestMethod.GET)
	public synchronized ModelAndView firstTime(ModelMap model) {
 
		
		return new ModelAndView("FirstTime","otp", new OTP());
 
	}
	
	@RequestMapping(value="/FirstTimeLogin", method = RequestMethod.POST)
	public synchronized String FirstTimeLoginValidation(ModelMap model,@ModelAttribute("otp") OTP otp,BindingResult result,Principal principal) {
 
		//validate the One Time Password Entered
		
		String oneTimePassword = otp.getotp();
	
		// Get the object from the OTP table
		ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
		LoginAttemptBO loginAttemptBO = (LoginAttemptBO)appContext.getBean("loginAttemptBO");
		OTPBO otpBO = (OTPBO)appContext.getBean("OTPBO");
		
		int userid=0;
		int empid=0;
		InternalAccount ia = null; 
		String name = principal.getName();
		
		ExternalAccount ea = loginAttemptBO.findAccountByNameExternal(name);
		if(ea == null){
			ia = loginAttemptBO.findAccountByNameInternal(name);
			if(ia != null){
				userid = 0;
				empid = ia.getEmployeeId();
				
			}
		}else{
			//external use case
			userid = ea.getUserid();
			empid = 0;		
		}
		OTP indatabase = otpBO.findAccountByName(userid,empid);
		
		//suppose user provided valid one-time-password
			
				java.util.Date current_date = new java.util.Date();
				java.sql.Timestamp current_timestamp = new java.sql.Timestamp(current_date.getTime());
				long current_time = current_timestamp.getTime();
				long db_time = indatabase.gettime().getTime();
				long time_diff = current_time - db_time; //5 minutes = 5 * 60 * 1000 (ms) =300,000
				// Need to be modified!!!
				if(oneTimePassword.equals(indatabase.getotp()) && time_diff < 300000){ 
					System.out.println("Ok!!!");
					
					// delete the one time login entry
					otpBO.delete(indatabase);
					//set first time login to false
					if(ea != null)
					{
						ea.setFirstTimeLogin(false);
						externalAccountBO.update(ea);
					}
					if(ia != null)
					{
						ia.setFirstTimeLogin(false);
						internalAccountBO.update(ia);
					}
					
					return "OTPValidationSuccess";
				}else{
					/*return "redirect:/j_spring_security_logout";*/
					return "OTPfailure";
				}
 
	}
	
	@RequestMapping(value="/Verify", method = RequestMethod.POST)
	public synchronized String VerifyPassword(ModelMap model,@ModelAttribute("forgot_user") InternalAccount forgot_user,Principal principal) 
	{
		boolean result;
		String errors = "";
		

		result = StringValidator.inputvalidation(forgot_user.getUsername(), "username");
		if(!result){
			errors = errors + "Please enter valid username;";
		}
		
		result = StringValidator.inputvalidation(forgot_user.getTelephone(), "number");
		if(!result){
			errors = errors + "Please enter valid phone number;";
		}

		result = StringValidator.inputvalidation(forgot_user.getEmail(), "email");
		if(!result){
			errors = errors + "Please enter valid email;";
		}
		
		result = StringValidator.inputvalidation(forgot_user.getSecurityanswer(), "genpattern");
		if(!result){
			errors = errors + "Please enter valid security answer;";
		}

		if(errors != "")
		{

			model.addAttribute("errors", errors);
			model.addAttribute("forgot_user", forgot_user);
	
				return "ForgetPassword";

		} else{
		ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
		LoginAttemptBO loginAttemptBO = (LoginAttemptBO)appContext.getBean("loginAttemptBO");
     	CreateExternalAccountBO createExternalAccountBO = (CreateExternalAccountBO)appContext.getBean("createExternalAccountBO");
     	CreateInternalAccountBO createInternalAccountBO = (CreateInternalAccountBO)appContext.getBean("createInternalAccountBO");

		String userType = loginAttemptBO.findRecoveryAccount(forgot_user.getUsername(), 
				forgot_user.getEmail(), forgot_user.getTelephone(), 
				forgot_user.getSecurityquestion(), forgot_user.getSecurityanswer());
		String otp_pwd = this.generateOTP();
		
		//find user from database and update password
		
		if(userType == "INTERNAL"){
			InternalAccount ia = loginAttemptBO.findAccountByNameInternal(forgot_user.getUsername());
			if(ia != null)
			{
			    try{
			        MessageDigest digest = MessageDigest.getInstance("SHA-256");
			        byte[] hash = digest.digest(otp_pwd.getBytes("UTF-8"));
			        StringBuffer hexString = new StringBuffer();

			        for (int i = 0; i < hash.length; i++) {
			            String hex = Integer.toHexString(0xff & hash[i]);
			            if(hex.length() == 1) hexString.append('0');
			            hexString.append(hex);
			        }
			        this.sendPwdRecoveryEmail(ia.getEmail(), ia.getUsername(), otp_pwd);
			        ia.setPassword(hexString.toString());
			        //ia.setPassword(otp_pwd);
			        createInternalAccountBO.update(ia);
			    } catch(Exception ex){
			       throw new RuntimeException(ex);
			    }
			}
			else
			{
				//both are null --> there's no matching table
				return "redirect:/j_spring_security_logout";
			}
			
		}
		else if(userType == "EXTERNAL")
		{
			//external use case
			ExternalAccount ea = loginAttemptBO.findAccountByNameExternal(forgot_user.getUsername());
			if(ea != null)
			{
				try{
					MessageDigest digest = MessageDigest.getInstance("SHA-256");
					byte[] hash = digest.digest(otp_pwd.getBytes("UTF-8"));
					StringBuffer hexString = new StringBuffer();

					for (int i = 0; i < hash.length; i++) {
						String hex = Integer.toHexString(0xff & hash[i]);
						if(hex.length() == 1) hexString.append('0');
						hexString.append(hex);
					}
					this.sendPwdRecoveryEmail(ea.getEmail(), ea.getUsername(), otp_pwd);
					ea.setPassword(hexString.toString());
					//ea.setPassword(otp_pwd);
					createExternalAccountBO.update(ea);
				} catch(Exception ex){
					throw new RuntimeException(ex);
				}
			}
			else
			{
				//both are null --> there's no matching table
				return "redirect:/j_spring_security_logout";
			}
		}
		else
		{
			return "RecoveryFailure";
		}
		return "RecoverySuccess";
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
	
	public String generateOTP(){
        String chars = "abcdefghijklmnopqrstuvwxyz"
                + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789";

	   final int PW_LENGTH = 20;
	   Random rnd = new SecureRandom();
	   StringBuilder pass = new StringBuilder();
	   for (int i = 0; i < PW_LENGTH; i++)
	       pass.append(chars.charAt(rnd.nextInt(chars.length())));
	   return pass.toString();
	}
	
	public synchronized void sendEmail(String mail_address, String username, String otp_pwd){

        //sending email part
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("group2.otp","group2group@");
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("group2.otp@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mail_address));
			message.setSubject("One Time Password for Sun Devil Bank of Arizona State (SDBAS)");
			message.setText("Dear our valued customer, " + username +
					"\n\n Please type following password in our system to validate your account!" +
					"\n\n " + otp_pwd +
					"\n\n FYI, this password will not be available after 5 minutes!!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		//end of email part
	}
	public synchronized void sendPwdRecoveryEmail(String mail_address, String username, String otp_pwd){

        //sending email part
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("group2.otp","group2group@");
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("group2.otp@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mail_address));
			message.setSubject("One Time Password for Sun Devil Bank of Arizona State (SDBAS)");
			message.setText("Dear our valued customer, " + username +
					"\n\n Please user following temporary password to login your account!" +
					"\n\n " + otp_pwd +
					"\n\n Thanks for using our system!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		//end of email part
	}
}
