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

import edu.asu.securebanking.businessobject.CreateExternalAccountBO;
import edu.asu.securebanking.businessobject.CreateInternalAccountBO;
import edu.asu.securebanking.businessobject.ExternalUserTransactionBO;
import edu.asu.securebanking.businessobject.IndividualUserBO;
import edu.asu.securebanking.businessobject.InternalUserTransactionBO;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.ExternalAccount;
import edu.asu.securebanking.model.ExternalUserTransaction;
import edu.asu.securebanking.model.InternalAccount;
import edu.asu.securebanking.model.InternalUserTransaction;
import edu.asu.securebanking.validator.StringValidator;

@Controller
@SessionAttributes
@RequestMapping("/transactionMonitoringEmployee")
public class TransactionMonitoringEmployeeController {

	@Autowired
	InternalUserTransactionBO internalTransactionBO;

	@Autowired
	CreateInternalAccountBO intBo;

	@Autowired
	ExternalUserTransactionBO externalTransactionBO;

	@Autowired
	CreateExternalAccountBO externalAccountBO;

	@Autowired
	IndividualUserBO individualUserBO;

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model){

		//return form view
		return "TransactionMonitoringEmployeeHomePage";
	}

	@RequestMapping(value="/internalUsersTransactions")
	public String internalUsers(ModelMap model) {
		InternalUserTransaction transaction = new InternalUserTransaction();

		//command object
		model.addAttribute("transaction", transaction);

		//return form view
		return "InternalUserTransaction";
	}

	@RequestMapping(value="/externalUsersTransactions")
	public String externalUsers(ModelMap model) {

		ExternalUserTransaction user = new ExternalUserTransaction();

		//command object
		model.addAttribute("user", user);

		//return form view
		return "ExternalUserTransaction";
	}



	@RequestMapping("/InternalUserTransaction")
	public ModelAndView showInternalUserTransactionForm() {
		return new ModelAndView("InternalUserTransactionCreation","command",new InternalUserTransaction());
	}

	@RequestMapping(value = "/addInternalUserTransaction", method = RequestMethod.POST)

	public synchronized ModelAndView create(@ModelAttribute("InternalUserTransaction") InternalUserTransaction internalUserTransaction) {

		internalUserTransaction.setStatus("approved");

		ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");

		InternalUserTransactionBO internalUserTransactionBO = (InternalUserTransactionBO)appContext.getBean("internalUserTransactionBO");

		internalUserTransactionBO.save(internalUserTransaction);

		return new ModelAndView("InternalUserTransactionCreation","command",new InternalUserTransaction());

	}

	@RequestMapping("/ExternalUserTransaction")
	public synchronized ModelAndView showExternalUserTransactionForm(Principal principal) {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 3){
			ModelAndView modelAndView = new ModelAndView("ExternalUserTransactionCreation");

			ExternalUserTransaction exttrans = new ExternalUserTransaction();
			exttrans.setStatus("Pending");
			modelAndView.addObject("exttrans", exttrans);
			modelAndView.addObject("currentuser", currentuser);

			return modelAndView;
		}else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}
	}

	@RequestMapping(value = "/addExternalUserTransaction", method = RequestMethod.POST)
	public synchronized ModelAndView create(@ModelAttribute("exttrans") ExternalUserTransaction externalUserTransaction, BindingResult result, Principal principal) {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);
		externalUserTransaction.setStatus("Approved");
		externalUserTransaction.setAccessGranted(1);

		if(currentuser.getDeptid() == 3){
			boolean res;
			String errors = "";

			if(externalUserTransaction.getAmountInvolved() < 1){
				errors = errors + "Please enter Amount greater than $1;";
			}

			if(externalUserTransaction.getTransType().matches("Transfer")){

				res = StringValidator.inputvalidation(externalUserTransaction.getTransDetail() , "onlynumbers");
				if(!res){
					errors = errors + "Please enter valid Account Number in Description for Transfer;";
				}
			}else{
				res = StringValidator.inputvalidation(externalUserTransaction.getTransDetail() , "genpattern");
				if(!res){
					errors = errors + "Please enter valid Description;";
				}
			}

			if(errors != "")
			{
				ModelAndView modelAndView = new ModelAndView("ExternalUserTransactionCreation");
				modelAndView.addObject("exttrans", externalUserTransaction);
				modelAndView.addObject("currentuser", currentuser);
				modelAndView.addObject("errors", errors);

				return modelAndView;

			} else{
				if(externalUserTransaction.getTransType().matches("Debit")){
					

					double amount = externalUserTransaction.getAmountInvolved();

					double currentBalance = 0.0;

					int userId = externalUserTransaction.getUserId();

					ExternalAccount externalAccount = externalAccountBO.findUserByid(userId);

					if(externalAccount != null)
					{
						currentBalance = externalAccount.getCurrentBalance();
					} else{
						errors = errors + "User ID not found;";
						ModelAndView modelAndView = new ModelAndView("ExternalUserTransactionCreation");
						modelAndView.addObject("exttrans", externalUserTransaction);
						modelAndView.addObject("currentuser", currentuser);
						modelAndView.addObject("errors", errors);

						return modelAndView;
					}
					// Check the current Balance  if not return error 

					if(currentBalance<amount)
					{
						errors = errors + "No sufficient balance;";
						ModelAndView modelAndView = new ModelAndView("ExternalUserTransactionCreation");
						modelAndView.addObject("exttrans", externalUserTransaction);
						modelAndView.addObject("currentuser", currentuser);
						modelAndView.addObject("errors", errors);

						return modelAndView;
					}

					//else process the transaction.
					else
					{
						//Update the current Balance 
						amount=amount*-1;

						currentBalance =  currentBalance + amount;

						externalAccount.setCurrentBalance(currentBalance);

						externalAccountBO.update(externalAccount);

						// Create New Transaction
						externalUserTransaction.setAmountInvolved(amount);

						externalTransactionBO.save(externalUserTransaction);

						ModelAndView modelAndView = new ModelAndView("genericsuccess");

						String message = "Transaction created successfully!";
						modelAndView.addObject("message", message);

						return modelAndView;
					}
				}
				else if(externalUserTransaction.getTransType().matches("Credit")){

					double amount = externalUserTransaction.getAmountInvolved();

					double currentBalance = 0.0;

					int userId = externalUserTransaction.getUserId();

					ExternalAccount externalAccount = externalAccountBO.findUserByid(userId);

					if(externalAccount != null)
					{
						currentBalance = externalAccount.getCurrentBalance();

						currentBalance += amount;

					}else{
						errors = errors + "User ID not found;";
						ModelAndView modelAndView = new ModelAndView("ExternalUserTransactionCreation");
						modelAndView.addObject("exttrans", externalUserTransaction);
						modelAndView.addObject("currentuser", currentuser);
						modelAndView.addObject("errors", errors);

						return modelAndView;
					}

					externalAccount.setCurrentBalance(currentBalance);

					externalAccountBO.update(externalAccount);

					// Create New Transaction
					externalUserTransaction.setAmountInvolved(amount);

					externalTransactionBO.save(externalUserTransaction);

					ModelAndView modelAndView = new ModelAndView("genericsuccess");

					String message = "Transaction created successfully!";
					modelAndView.addObject("message", message);

					return modelAndView;

				}
				else {
					double amountToTransfer = externalUserTransaction.getAmountInvolved();

					double currentSourceBalance = 0.0;

					int userId = externalUserTransaction.getUserId();

					ExternalAccount sourceExternalAccount = externalAccountBO.findUserByid(userId);

					if(sourceExternalAccount != null)
					{
						currentSourceBalance = sourceExternalAccount.getCurrentBalance();
					}else{
						errors = errors + "User ID not found;";
						ModelAndView modelAndView = new ModelAndView("ExternalUserTransactionCreation");
						modelAndView.addObject("exttrans", externalUserTransaction);
						modelAndView.addObject("currentuser", currentuser);
						modelAndView.addObject("errors", errors);

						return modelAndView;
					}
					// Check the current Balance  if not return error 

					if(currentSourceBalance<amountToTransfer)
					{
						errors = errors + "No sufficient balance;";
						ModelAndView modelAndView = new ModelAndView("ExternalUserTransactionCreation");
						modelAndView.addObject("exttrans", externalUserTransaction);
						modelAndView.addObject("currentuser", currentuser);
						modelAndView.addObject("errors", errors);

						return modelAndView;
					}

					//else process the transaction.
					else
					{

						// Find the account from database where money needs to be transfered.

						String accountNumber = externalUserTransaction.getTransDetail();


						ExternalAccount destinationExternalAccount = individualUserBO.getAccountByAccountNumber(accountNumber);

						if(destinationExternalAccount == null ||(sourceExternalAccount.getAccountNo().equals(destinationExternalAccount.getAccountNo())))
						{
							errors = errors + "Please enter valid Account Number;";
							ModelAndView modelAndView = new ModelAndView("ExternalUserTransactionCreation");
							modelAndView.addObject("exttrans", externalUserTransaction);
							modelAndView.addObject("currentuser", currentuser);
							modelAndView.addObject("errors", errors);

							return modelAndView;

						}

						// Update Source Account Balance 
						sourceExternalAccount.setCurrentBalance(currentSourceBalance-amountToTransfer);

						externalAccountBO.update(sourceExternalAccount);

						// Create Transaction For Source Account 

						externalUserTransaction.setTransDetail("Transfer To A/C" + accountNumber);

						externalUserTransaction.setAmountInvolved(amountToTransfer*-1);

						externalTransactionBO.save(externalUserTransaction);

						// 	Update Destination Account Balance 

						double destinationBalance = destinationExternalAccount.getCurrentBalance();

						destinationBalance+=amountToTransfer;

						destinationExternalAccount.setCurrentBalance(destinationBalance);

						externalAccountBO.update(destinationExternalAccount);

						//Create Transaction for Destination Balance 		

						externalUserTransaction.setUserId(destinationExternalAccount.getUserid());

						externalUserTransaction.setTransDetail("Transfer From A/C" + sourceExternalAccount.getAccountNo());

						externalUserTransaction.setAmountInvolved(amountToTransfer);

						externalTransactionBO.save(externalUserTransaction);

						ModelAndView modelAndView = new ModelAndView("genericsuccess");

						String message = "Transaction created successfully!";
						modelAndView.addObject("message", message);

						return modelAndView;

					}

				}

			}
		}else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}
	}

	@RequestMapping(value="/findInternalTransaction", method = RequestMethod.POST)
	public synchronized ModelAndView findtransactionbyid(@ModelAttribute("transaction") InternalUserTransaction transaction,
			BindingResult result) throws InternalException {
		ModelAndView modelAndView = new ModelAndView("findInternalTransaction");
		InternalUserTransaction transactionbyid = internalTransactionBO.findTransactionById(transaction.getTransId());
		System.out.println("transaction.getEmployeeId()"+ transactionbyid.getEmployeeId() + "transaction.getDescription()" + transactionbyid.getDescription()+"transaction.getTransId()" + transactionbyid.getTransId()+" transaction.getStatus()" +transactionbyid.getStatus());
		modelAndView.addObject("transactionbyid", transactionbyid);
		return modelAndView;
	}

	@RequestMapping(value="/findExternalTransaction", method = RequestMethod.POST)
	public synchronized ModelAndView findExternalTransactionById(@ModelAttribute("transaction") ExternalUserTransaction transaction,
			BindingResult result) throws InternalException {
		ModelAndView modelAndView = new ModelAndView("FindExternalTransaction");
		ExternalUserTransaction transactionbyid = externalTransactionBO.findTransactionById(transaction.getTransId());
		modelAndView.addObject("transactionbyid", transactionbyid);
		return modelAndView;
	}

	@RequestMapping(value="/deletetransaction")
	public synchronized ModelAndView deletetransactionbyid(@RequestParam("transId")Integer transId) {

		ModelAndView modelAndView = new ModelAndView("internalTransactionDeleted");

		internalTransactionBO.delete(transId);

		modelAndView.addObject("message", transId);

		return modelAndView;
	}

	@RequestMapping(value="/authorizeexternaltransaction")
	public synchronized ModelAndView authorizetransactionbyid(@ModelAttribute("transactionbyid") ExternalUserTransaction transaction) {

		ModelAndView modelAndView = new ModelAndView("TransactionMonAuthTrans");


		ExternalUserTransaction transactionbyid = externalTransactionBO.findTransactionById(transaction.getTransId());

		transactionbyid.setStatus("Approved");;

		externalTransactionBO.update(transactionbyid);

		modelAndView.addObject("message", transaction.getTransId());

		return modelAndView;
	}

	@RequestMapping(value="/updatetransaction")
	public synchronized ModelAndView displayUpdatePage(@RequestParam("transId")Integer transId) {


		ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");

		InternalUserTransactionBO internalUserTransactionBO = (InternalUserTransactionBO)appContext.getBean("internalUserTransactionBO");

		InternalUserTransaction internalTransaction = internalUserTransactionBO.findTransactionById(transId);

		return new ModelAndView("updateInternalTransaction","command",internalTransaction);

	}

	@RequestMapping(value="/updateInternalUserTransaction")
	public synchronized ModelAndView updatetransaction(@ModelAttribute("command") InternalUserTransaction transaction,
			BindingResult result) {


		ModelAndView modelAndView = new ModelAndView("internalUserTransactionUpdated");

		internalTransactionBO.update(transaction);

		modelAndView.addObject("message", transaction.getTransId());

		return modelAndView;

	}

	@RequestMapping(value="/updateexternaltransaction")
	public synchronized ModelAndView displayExternalUpdatePage(@ModelAttribute("transactionbyid") ExternalUserTransaction transaction, Principal principal) {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 3){
			ModelAndView modelAndView = new ModelAndView("UpdateExternalTransaction");
			ExternalUserTransaction externalTransaction = externalTransactionBO.findTransactionById(transaction.getTransId());
			modelAndView.addObject("currentuser", currentuser);
			modelAndView.addObject("externalTransaction", externalTransaction);

			return modelAndView;
		}else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}

	}

	@RequestMapping(value="/updateExternalUserTransaction")
	public synchronized ModelAndView updateExternalTransaction(@ModelAttribute("externalTransaction") ExternalUserTransaction transaction,
			BindingResult result, Principal principal) {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 3){
			boolean res;
			String errors = "";


			/*res = StringValidator.inputvalidation(transaction.getTransDetail() , "genpattern");
			if(!res){
				errors = errors + "Please enter valid Description;";
			}*/
			if(errors != "")
			{
				ModelAndView modelAndView = new ModelAndView("UpdateExternalTransaction");
				modelAndView.addObject("externalTransaction", transaction);
				modelAndView.addObject("currentuser", currentuser);
				modelAndView.addObject("errors", errors);

				return modelAndView;

			} else{

				ModelAndView modelAndView = new ModelAndView("ExternalUserTransactionUpdated");
				externalTransactionBO.update(transaction);
				modelAndView.addObject("message", transaction.getTransId());
				return modelAndView;
			}
		}else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}

	}

	@RequestMapping(value="/displayinternaltransactions")

	public synchronized ModelAndView listOfAllInternalTransactions() {
		ModelAndView modelAndView = new ModelAndView("listInternalTransactions");
		List<InternalUserTransaction> transactions = internalTransactionBO.getAllInternalUserTransactions();
		modelAndView.addObject("transactions", transactions);
		return modelAndView;
	}

	@RequestMapping(value="/displayexternaltransactions")

	public ModelAndView listOfAllExternalTransactions(Principal principal) {
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);

		if(currentuser.getDeptid() == 3){
			ModelAndView modelAndView = new ModelAndView("ListExternalTransactions");
			List<ExternalUserTransaction> transactions = externalTransactionBO.getAllExternalUsersTransactions();
			modelAndView.addObject("transactions", transactions);
			ExternalUserTransaction transactionbyid = new ExternalUserTransaction();
			modelAndView.addObject("transactionbyid", transactionbyid);
			modelAndView.addObject("currentuser", currentuser);
			return modelAndView;
		}else{
			ModelAndView modelAndView = new ModelAndView("accessdenied");

			String accessdenied = "Access Denied";
			modelAndView.addObject("accessdenied", accessdenied);

			return modelAndView;
		}
	}

	@RequestMapping(value="/deleteexternaltransaction")
	public synchronized ModelAndView deleteExternalTransactionById(@ModelAttribute("transactionbyid") ExternalUserTransaction transaction) {

		

		/*if( transaction.getTransType().matches("Credit")){*/
			ModelAndView modelAndView = new ModelAndView("ExternalUserTransactionDeleted");
			/*double amount = transaction.getAmountInvolved();
			int userId = transaction.getUserId();

			ExternalAccount externalAccount = externalAccountBO.findUserByid(userId);
			amount=amount*-1;
			double currentBalance = externalAccount.getCurrentBalance();
			currentBalance =  currentBalance + amount;

			externalAccount.setCurrentBalance(currentBalance);

			externalAccountBO.update(externalAccount);*/

			externalTransactionBO.delete(transaction.getTransId());

			modelAndView.addObject("message", transaction.getTransId());

			return modelAndView;
	/*	}
		else {
			ModelAndView modelAndView = new ModelAndView("genericsuccess");
			
			modelAndView.addObject("message", "Transfer type transactions cannot be deleted!");

			return modelAndView;
		}*/
	}


}
