package edu.asu.securebanking.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import edu.asu.securebanking.businessobject.CreateInternalAccountBO;
import edu.asu.securebanking.businessobject.ExternalUserTransactionBO;
import edu.asu.securebanking.businessobject.InternalUserTransactionBO;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.ExternalUserTransaction;
import edu.asu.securebanking.model.InternalAccount;
import edu.asu.securebanking.model.InternalUserTransaction;
import edu.asu.securebanking.validator.StringValidator;

@Controller
@SessionAttributes
@RequestMapping("/ITRegularEmployee")
public class ITRegularEmployeeController {

	@Autowired
	CreateInternalAccountBO intBo;

	@Autowired
	InternalUserTransactionBO transactionBO;

	/*	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model){

		InternalUserTransaction transaction = new InternalUserTransaction();

		//command object
		model.addAttribute("transaction", transaction);

		//return form view
		return "ITUserTransaction";
	}
	 */
	@RequestMapping(value="/internalUsers")
	public synchronized ModelAndView internalUsers(Principal principal) {

		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 2){
			ModelAndView modelAndView = new ModelAndView("ITUserTransaction");
			modelAndView.addObject("currentuser", currentuser);

			InternalUserTransaction transaction = new InternalUserTransaction();
			modelAndView.addObject("transaction", transaction);

			return modelAndView;
		}else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}
	}

	@RequestMapping("/ITInternalUserTransaction")
	public synchronized ModelAndView showInternalUserTransactionForm(Principal principal) {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 2){
			ModelAndView modelAndView = new ModelAndView("ITInternalUserTransactionCreation");
			modelAndView.addObject("currentuser", currentuser);

			InternalUserTransaction transaction = new InternalUserTransaction();
			modelAndView.addObject("transaction", transaction);
			return modelAndView;
		}else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}
	}
	@RequestMapping(value = "/addInternalUserTransaction", method = RequestMethod.GET)
	public ModelAndView createget() {

		ModelAndView modelAndView = new ModelAndView("accessdenied");

		String accessdenied = "Access Denied";
		modelAndView.addObject("accessdenied", accessdenied);

		return modelAndView;

	}

	@RequestMapping(value = "/addInternalUserTransaction", method = RequestMethod.POST)
	public synchronized ModelAndView create(@ModelAttribute("transaction") InternalUserTransaction transaction, Principal principal) {

		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		boolean result;
		String errors = "";
		

		result = StringValidator.inputvalidation(transaction.getTransType(), "genpattern");
		if(!result){
			errors = errors + "Please enter valid Transaction Type;";
		}

		result = StringValidator.inputvalidation(transaction.getDescription(), "genpattern");
		if(!result){
			errors = errors + "Please enter valid Description;";
		}

		if(errors != "")
		{
			ModelAndView modelAndView = new ModelAndView("ITInternalUserTransactionCreation");
			modelAndView.addObject("currentuser", currentuser);

			InternalUserTransaction trans = new InternalUserTransaction();
			modelAndView.addObject("transaction", trans);

			modelAndView.addObject("errors", errors);

			return modelAndView;

		} else{

			transaction.setStatus("approved");
			transaction.setDeptId(2);//2:IT dept

			String res = transactionBO.save(transaction);

			if(res.matches("fail")){
				errors = errors + "Employee ID does'nt exist!";
				ModelAndView modelAndView = new ModelAndView("ITInternalUserTransactionCreation");
				modelAndView.addObject("currentuser", currentuser);

				InternalUserTransaction trans = new InternalUserTransaction();
				modelAndView.addObject("transaction", trans);

				modelAndView.addObject("errors", errors);

				return modelAndView;

			}
			else{

				ModelAndView modelAndView = new ModelAndView("genericsuccess");

				String message = "Created ticket successfully";
				modelAndView.addObject("message", message);

				return modelAndView;
			}
		}

	}

	@RequestMapping(value = "/findInternalTransaction", method = RequestMethod.GET)
	public synchronized ModelAndView findtransactionbyidget() {

		ModelAndView modelAndView = new ModelAndView("accessdenied");

		String accessdenied = "Access Denied";
		modelAndView.addObject("accessdenied", accessdenied);

		return modelAndView;

	}


	@RequestMapping(value="/findInternalTransaction", method = RequestMethod.POST)
	public synchronized ModelAndView findtransactionbyid(@ModelAttribute("transaction") InternalUserTransaction transaction,
			BindingResult result) throws InternalException {
		ModelAndView modelAndView = new ModelAndView("ITfindInternalTransaction");
		InternalUserTransaction transactionbyid = transactionBO.findTransactionByIdAndDeptId(transaction.getTransId(),2);//2:IT dept
		System.out.println("transaction.getEmployeeId()"+ transactionbyid.getEmployeeId() + "transaction.getDescription()" + transactionbyid.getDescription()+"transaction.getTransId()" + transactionbyid.getTransId()+" transaction.getStatus()" +transactionbyid.getStatus());
		modelAndView.addObject("transactionbyid", transactionbyid);
		return modelAndView;
	}

	@RequestMapping(value="/deletetransaction")
	public synchronized ModelAndView deletetransactionbyid(@ModelAttribute("trans") InternalUserTransaction trans, Principal principal) {

		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 2){

			ModelAndView modelAndView = new ModelAndView("genericsuccess");

			transactionBO.delete(trans.getTransId());

			String message = "Ticket deleted successfully!";
			modelAndView.addObject("message", message);

			return modelAndView;
		}else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}
	}

	@RequestMapping(value="/updatetransaction")
	public synchronized ModelAndView displayUpdatePage(@ModelAttribute("trans") InternalUserTransaction trans, Principal principal) {

		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 2){
			InternalUserTransaction internalTransaction = transactionBO.findTransactionById(trans.getTransId());
			ModelAndView modelAndView = new ModelAndView("ITupdateInternalTransaction");

			modelAndView.addObject("internalTransaction", internalTransaction);
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

	@RequestMapping(value="/updateInternalUserTransaction")
	public synchronized ModelAndView updatetransaction(@ModelAttribute("internalTransaction") InternalUserTransaction transaction,
			BindingResult result, Principal principal) {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 2){
			boolean res;
			String errors = "";
			
			res = StringValidator.inputvalidation(transaction.getDescription(), "genpattern");
			if(!res){
				errors = errors + "Please enter valid Description;";
			}

			if(errors != "")
			{
				ModelAndView modelAndView = new ModelAndView("ITupdateInternalTransaction");
				modelAndView.addObject("currentuser", currentuser);

				modelAndView.addObject("transaction", transaction);

				modelAndView.addObject("errors", errors);

				return modelAndView;

			} else{
			transaction.setDeptId(2);//2:IT dept
			transactionBO.update(transaction);

			ModelAndView modelAndView = new ModelAndView("genericsuccess");

			String message = "Ticket Updated Successfully!";
			modelAndView.addObject("message", message);

			return modelAndView;
			}
		}
		else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}

	}

	@RequestMapping(value="/displaytransactions")

	public synchronized ModelAndView listOfUTransactions(Principal principal) {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 2){
			ModelAndView modelAndView = new ModelAndView("ITlistInternalTransactions");
			List<InternalUserTransaction> transactions = transactionBO.getDeptInternalUserTransactions(2); //2:IT dept
			System.out.println(transactions.listIterator());
			modelAndView.addObject("currentuser", currentuser);
			modelAndView.addObject("transactions", transactions);
			InternalUserTransaction trans = new InternalUserTransaction();
			modelAndView.addObject("trans", trans);
			return modelAndView;
		}
		else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}
	}

}
