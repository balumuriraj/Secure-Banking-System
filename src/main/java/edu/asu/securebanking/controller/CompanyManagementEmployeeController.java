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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.securebanking.businessobject.CreateInternalAccountBO;
import edu.asu.securebanking.businessobject.InternalUserTransactionBO;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.InternalAccount;
import edu.asu.securebanking.model.InternalUserTransaction;
import edu.asu.securebanking.validator.StringValidator;

@Controller
@SessionAttributes
@RequestMapping("/CompanyManagement")
public class CompanyManagementEmployeeController {

	@Autowired
	InternalUserTransactionBO transactionBO;

	@Autowired
	CreateInternalAccountBO intBo;

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model){

		InternalUserTransaction transaction = new InternalUserTransaction();

		//command object
		model.addAttribute("transaction", transaction);

		//return form view
		return "CompanyManagementEmployeeHomePage";
	}

	@RequestMapping("/CompanyManagementTransaction")
	public ModelAndView showInternalUserTransactionForm(Principal principal) {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 5){
			ModelAndView modelAndView = new ModelAndView("CompanyManagementTransactionCreation");

			InternalUserTransaction trans = new InternalUserTransaction();
			modelAndView.addObject("trans", trans);
			modelAndView.addObject("currentuser", currentuser);

			return modelAndView;
		} else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}
	}




	@RequestMapping(value = "/addCompanyManagementTransaction", method = RequestMethod.POST)
	public synchronized ModelAndView create(@ModelAttribute("trans") InternalUserTransaction trans, Principal principal) {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 5){
			boolean result;
			String errors = "";


			result = StringValidator.inputvalidation(trans.getTransType(), "genpattern");
			if(!result){
				errors = errors + "Please enter valid Transaction Type;";
			}

			result = StringValidator.inputvalidation(trans.getDescription(), "genpattern");
			if(!result){
				errors = errors + "Please enter valid Description;";
			}

			if(errors != "")
			{
				ModelAndView modelAndView = new ModelAndView("CompanyManagementTransactionCreation");
				modelAndView.addObject("currentuser", currentuser);

				InternalUserTransaction trans1 = new InternalUserTransaction();
				modelAndView.addObject("trans", trans1);

				modelAndView.addObject("errors", errors);

				return modelAndView;

			} else{

				trans.setStatus("approved");

				trans.setDeptId(5);//5:Schedule Meeting

				String res =  transactionBO.save(trans);

				if(res.matches("fail")){
					errors = errors + "Employee ID does'nt exist!";
					ModelAndView modelAndView = new ModelAndView("CompanyManagementTransactionCreation");
					modelAndView.addObject("currentuser", currentuser);

					InternalUserTransaction trans1 = new InternalUserTransaction();
					modelAndView.addObject("trans", trans1);

					modelAndView.addObject("errors", errors);

					return modelAndView;

				}
				else{

					ModelAndView modelAndView = new ModelAndView("genericsuccess");

					String message = "Meeting Scheduled successfully";
					modelAndView.addObject("message", message);

					return modelAndView;
				}
			}
		} else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}

	}

	@RequestMapping(value = "/addCompanyManagementTransaction", method = RequestMethod.GET)
	public ModelAndView createget(@ModelAttribute("trans") InternalUserTransaction trans, Principal principal) {

		ModelAndView modelAndView = new ModelAndView("accessdenied");

		String accessdenied = "Access Denied";
		modelAndView.addObject("accessdenied", accessdenied);

		return modelAndView;


	}

	@RequestMapping(value="/displayMeetings")

	public synchronized ModelAndView listOfUTransactions(Principal principal) {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 5){
			ModelAndView modelAndView = new ModelAndView("CompanyManagementListMeetings");
			List<InternalUserTransaction> transactions = transactionBO.getDeptInternalUserTransactions(5); //5:Company Management
			System.out.println(transactions.listIterator());
			InternalUserTransaction trans = new InternalUserTransaction();
			modelAndView.addObject("transactions", transactions);
			modelAndView.addObject("trans", trans);
			modelAndView.addObject("currentuser", currentuser);
			return modelAndView;
		} else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}
	}

	@RequestMapping(value="/updateMeeting")
	public synchronized ModelAndView displayUpdatePage(@ModelAttribute("command") InternalUserTransaction transaction, Principal principal) {

		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 5){

			InternalUserTransaction internalTransaction = transactionBO.findTransactionById(transaction.getTransId());
			ModelAndView modelAndView = new ModelAndView("CompanyManagementMeetingUpdate");

			
			modelAndView.addObject("internalTransaction", internalTransaction);
			modelAndView.addObject("currentuser", currentuser);

			return modelAndView;

		} else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}

	}



	@RequestMapping(value="/updateCompanyMeeting")
	public synchronized ModelAndView updateCompanyMeeting(@ModelAttribute("trans") InternalUserTransaction transaction,
			BindingResult result,Principal principal) {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 5){
			boolean res;
			String errors = "";

			res = StringValidator.inputvalidation(transaction.getDescription(), "genpattern");
			if(!res){
				errors = errors + "Please enter valid Description;";
			}

			if(errors != "")
			{
				ModelAndView modelAndView = new ModelAndView("CompanyManagementMeetingUpdate");
				modelAndView.addObject("currentuser", currentuser);

				modelAndView.addObject("internalTransaction", transaction);

				modelAndView.addObject("errors", errors);

				return modelAndView;

			} else{

			transaction.setDeptId(5);//5: Company Management

			transactionBO.update(transaction);

			return new ModelAndView("CompanyManagementMeetingUpdated");
			}
		} else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}
	}

	@RequestMapping(value="/deleteMeeting")
	public synchronized ModelAndView deletetransactionbyid(@ModelAttribute("trans") InternalUserTransaction transaction,Principal principal) {

		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 5){
			ModelAndView modelAndView = new ModelAndView("CompanyManagementMeetingDeleted");

			transactionBO.delete(transaction.getTransId());

			return modelAndView;
		} else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}
	}

	@RequestMapping(value="/findMeeting", method = RequestMethod.POST)
	public synchronized ModelAndView findtransactionbyid(@ModelAttribute("transaction") InternalUserTransaction transaction,
			BindingResult result, Principal principal) throws InternalException {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 5){
			ModelAndView modelAndView = new ModelAndView("CompanyManagementFindMeeting");
			InternalUserTransaction transactionbyid = transactionBO.findTransactionByIdAndDeptId(transaction.getTransId(),5);//5:Company Management
			System.out.println("transaction.getEmployeeId()"+ transactionbyid.getEmployeeId() + "transaction.getDescription()" + transactionbyid.getDescription()+"transaction.getTransId()" + transactionbyid.getTransId()+" transaction.getStatus()" +transactionbyid.getStatus());
			modelAndView.addObject("transactionbyid", transactionbyid);
			return modelAndView;

		} else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}
	}
	@RequestMapping(value="/findMeeting", method = RequestMethod.GET)
	public synchronized ModelAndView findtransactionbyidget(@ModelAttribute("transaction") InternalUserTransaction transaction,
			BindingResult result) throws InternalException {

		ModelAndView modelAndView = new ModelAndView("accessdenied");

		String accessdenied = "Access Denied";
		modelAndView.addObject("accessdenied", accessdenied);

		return modelAndView;

	}
}
