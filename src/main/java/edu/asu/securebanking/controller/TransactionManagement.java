package edu.asu.securebanking.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.securebanking.businessobject.CreateExternalAccountBO;
import edu.asu.securebanking.businessobject.CreateInternalAccountBO;
import edu.asu.securebanking.businessobject.CreditcardRequestsBO;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.CreditcardRequests;
import edu.asu.securebanking.model.ExternalAccount;
import edu.asu.securebanking.model.InternalAccount;
import edu.asu.securebanking.model.InternalUserTransaction;
import edu.asu.securebanking.model.PiiAuth;
import edu.asu.securebanking.validator.StringValidator;

@Controller
@RequestMapping("/transactionManagement")
public class TransactionManagement {

	@Autowired
	CreateInternalAccountBO intBo;

	@Autowired
	CreateExternalAccountBO extBo;

	@Autowired
	CreditcardRequestsBO ccreqsBo;

	@RequestMapping(method = RequestMethod.GET)
	public synchronized ModelAndView initForm(ModelMap model, Principal principal){


		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 1){
			ModelAndView modelAndView = new ModelAndView("salestransactionManagement");
			modelAndView.addObject("currentuser", currentuser);

			ExternalAccount extuser = new ExternalAccount();
			modelAndView.addObject("extuser", extuser);

			System.out.println("enteringcc");
			List<ExternalAccount> ccreqs = extBo.getccreq();
			if(ccreqs != null){
				modelAndView.addObject("ccreqs", ccreqs);
			}

			List<CreditcardRequests> appccreqs = ccreqsBo.getallCreditcardRequests();
			modelAndView.addObject("appccreqs", appccreqs);

			return modelAndView;

		} else if(currentuser.getDeptid() == 4){
			ModelAndView modelAndView = new ModelAndView("hrtransactionManagement");
			modelAndView.addObject("currentuser", currentuser);

			InternalAccount user = new InternalAccount();
			modelAndView.addObject("user", user);

			return modelAndView;
		} else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}


	}

	/*----------------HR----------------------*/

	@RequestMapping(value="/displayusers")
	public synchronized ModelAndView listOfUsers(@ModelAttribute("currentuser") InternalAccount currentuser,
			BindingResult result) {
		if(currentuser.getDeptid() == 4){
			ModelAndView modelAndView = new ModelAndView("listallinternalusers");
			List<InternalAccount> users = intBo.getAllInternalUserAccounts();
			System.out.println(users.listIterator());
			modelAndView.addObject("users", users);
			modelAndView.addObject("currentuser", currentuser);
			return modelAndView;
		}
		else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}
	}

	@RequestMapping(value="/updatesalary", method = RequestMethod.GET)
	public synchronized ModelAndView updatesalaryget(@ModelAttribute("user") InternalAccount user,
			BindingResult result) throws InternalException {
		ModelAndView modelAndView = new ModelAndView("accessdenied");

		String accessdenied = "Access Denied";
		modelAndView.addObject("accessdenied", accessdenied);

		return modelAndView;
	}

	@RequestMapping(value="/updatesalary", method = RequestMethod.POST)
	public synchronized ModelAndView updatesalary(@ModelAttribute("user") InternalAccount user,
			BindingResult result, Principal principal) throws InternalException {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		ModelAndView modelAndView = new ModelAndView("internalmgmtsuccessmsg");
		intBo.updatesalary(user.getEmployeeId(), user.getSalary());
		System.out.println("Salary updated!");
		String message = "Salary for Employee ID "+user.getEmployeeId()+ " updated!";
		modelAndView.addObject("message", message);
		return modelAndView;

	}


	/*----------------Sales----------------------*/

	@RequestMapping(value="/displayexternalusers")
	public synchronized ModelAndView displayexternalusers(Principal principal) {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);
		if(currentuser.getDeptid() == 1){
			ModelAndView modelAndView = new ModelAndView("listExternalUsers");
			List<ExternalAccount> users = extBo.getdetails();
			System.out.println(users.listIterator());
			modelAndView.addObject("users", users);
			return modelAndView;
		}else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}
	}

	@RequestMapping(value="/approveccrequest", method = RequestMethod.POST)
	public synchronized ModelAndView approveccrequest(@ModelAttribute("extuser") ExternalAccount extuser,
			BindingResult result) throws InternalException {
		ModelAndView modelAndView = new ModelAndView("internalmgmtsuccessmsg");
		ccreqsBo.approved(extuser.getAccountNo(), "yes");
		System.out.println("Credit Card Request approved!");
		String message = "Credit Card Request approved for Account No "+extuser.getAccountNo()+" !";
		modelAndView.addObject("message", message);
		return modelAndView;
	}

	@RequestMapping(value="/approveccrequest", method = RequestMethod.GET)
	public synchronized ModelAndView approveccrequestget(@ModelAttribute("extuser") ExternalAccount extuser,
			BindingResult result) throws InternalException {
		ModelAndView modelAndView = new ModelAndView("accessdenied");

		String accessdenied = "Access Denied";
		modelAndView.addObject("accessdenied", accessdenied);

		return modelAndView;
	}

	@RequestMapping(value="/rejectccrequest", method = RequestMethod.POST)
	public synchronized ModelAndView rejectccrequest(@ModelAttribute("extuser") ExternalAccount extuser,
			BindingResult result) throws InternalException {
		ModelAndView modelAndView = new ModelAndView("internalmgmtsuccessmsg");
		ccreqsBo.rejected(extuser.getAccountNo());
		System.out.println("Credit Card Request rejected!");
		String message = "Credit Card Request rejected for Account No "+extuser.getAccountNo()+" !";
		modelAndView.addObject("message", message);
		return modelAndView;
	}

	@RequestMapping(value="/rejectccrequest", method = RequestMethod.GET)
	public synchronized ModelAndView rejectccrequestget(@ModelAttribute("extuser") ExternalAccount extuser,
			BindingResult result) throws InternalException {
		ModelAndView modelAndView = new ModelAndView("accessdenied");

		String accessdenied = "Access Denied";
		modelAndView.addObject("accessdenied", accessdenied);

		return modelAndView;
	}

}
