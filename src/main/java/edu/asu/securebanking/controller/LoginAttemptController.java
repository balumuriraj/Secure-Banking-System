package edu.asu.securebanking.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.securebanking.businessobject.LoginAttemptBO;
import edu.asu.securebanking.businessobject.ExternalUserTransactionBO;
import edu.asu.securebanking.businessobject.LoginAttemptBO;
import edu.asu.securebanking.model.ExternalAccount;
import edu.asu.securebanking.model.ExternalUserTransaction;
import edu.asu.securebanking.model.LoginAttempt;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

@Controller
@SessionAttributes
public class LoginAttemptController {

	@RequestMapping("/LoginAttempt")
	public synchronized ModelAndView showLoginAttemptForm( 
			@RequestParam("j_username")String id, 
			@RequestParam("j_password")String pwd, HttpServletRequest req) { 

		System.out.println("show me the way!!!");
		ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");

		LoginAttemptBO loginAttemptBO = (LoginAttemptBO)appContext.getBean("loginAttemptBO");

		String role = "";

		String remoteAddr = req.getRemoteAddr();
		ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
		reCaptcha.setPrivateKey("6LcOO-kSAAAAAM2wCrSSw-0bB-cWMfTxH5YxGUIs");

		String challenge = req.getParameter("recaptcha_challenge_field");
		String uresponse = req.getParameter("recaptcha_response_field");
		ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

		System.out.println(role); 
		if(!role.equals("empty") && reCaptchaResponse.isValid()){
			return new ModelAndView("LoginSuccess");
		}else{
			return new ModelAndView("LoginFailure");
		}
	}

}