package edu.asu.securebanking.controller;


import javax.servlet.http.HttpSession;
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

import edu.asu.securebanking.businessobject.PassKeyBO;
import edu.asu.securebanking.model.PassKey;
import edu.asu.securebanking.validator.GenericValidator;

@Controller
@RequestMapping("/PassKey")
public class PassKeyController {

	@Autowired
	GenericValidator passkeyValidator;

	@Autowired
	PassKeyBO passKeyBO;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(passkeyValidator);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model) {
		PassKey passKey = new PassKey();

		//command object
		model.addAttribute("passKey", passKey);
		return "passKey";
	}

	@RequestMapping(method = RequestMethod.POST)
	public synchronized ModelAndView processSubmit(
			@Validated @ModelAttribute("passKey") PassKey passKey,
			BindingResult result, SessionStatus status, HttpSession session) 
	{
		System.out.println("Entering validator!");
		if (result.hasErrors()) {
			//if validator failed
			System.out.println("checking error!");
			ModelAndView modelAndView = new ModelAndView("passKey");
			return modelAndView;
		} else {

			System.out.println("check1");
			PassKey passKeyTemp = passKeyBO.findPassKey(passKey.getPassKey());
			System.out.println("check2");
			ModelAndView modelAndView;
			if(passKeyTemp != null)
			{
				System.out.println("check3");
				if(passKeyTemp.isAccountType())
				{
					session.setAttribute("passkey", passKeyTemp.getPassKey());
					session.setAttribute("passkeycheck", "true");
					modelAndView  = new ModelAndView("redirect:/ExternalRegistration");
				}
				else
				{
					session.setAttribute("passkey", passKeyTemp.getPassKey());
					session.setAttribute("passkeycheck", "true");
					modelAndView = new ModelAndView("redirect:/InternalRegistration");
				}
				return modelAndView;
			}
			System.out.println("check4");
			return new ModelAndView("redirect:/Passkeyfailure");

		}
	}

}
