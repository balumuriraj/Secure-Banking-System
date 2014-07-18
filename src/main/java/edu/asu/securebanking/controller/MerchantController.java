package edu.asu.securebanking.controller;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.securebanking.businessobject.CreateExternalAccountBO;
import edu.asu.securebanking.businessobject.CreditcardRequestsBO;
import edu.asu.securebanking.businessobject.ExternalUserTransactionBO;
import edu.asu.securebanking.businessobject.MerchantsBO;
import edu.asu.securebanking.businessobject.PkiBO;
import edu.asu.securebanking.model.CreditcardRequests;
import edu.asu.securebanking.model.ExternalAccount;
import edu.asu.securebanking.model.ExternalUserTransaction;
import edu.asu.securebanking.model.FileUpload;
import edu.asu.securebanking.model.Pki;
import edu.asu.securebanking.validator.StringValidator;


@Controller
@SessionAttributes
@RequestMapping("/Merchant")
public class MerchantController {
	
	@Autowired
	MerchantsBO merchantsBO;
	
	@Autowired
	CreateExternalAccountBO externalAccountBO;
	
	@Autowired
	ExternalUserTransactionBO externalTransactionBO;
	
	@Autowired
	CreditcardRequestsBO ccreqsBo;
	

	@Autowired
	PkiBO pkiBO;
	
	/*@RequestMapping(method = RequestMethod.GET)
	public ModelAndView initForm(ModelMap model){

		//return form view
		return new ModelAndView("MerchantHomePage","merchant", new ExternalAccount());
	}*/
	
	 @RequestMapping(value="/viewMerchantTransactions")
	 
		public synchronized ModelAndView listOflTransactions(Principal principal) {
		 
		 String name = principal.getName();
			ExternalAccount currentuser = externalAccountBO.findAccountByName(name);
		 
			ModelAndView modelAndView = new ModelAndView("MerchantTransactions");
			
			List<ExternalUserTransaction> transactions = merchantsBO.getAllMerchantsTransactions(currentuser.getUserid());
			
			modelAndView.addObject("transactions", transactions);
			
			return modelAndView;
		}

	 @RequestMapping(value="/merchantAccountManagement")
	 
		public synchronized ModelAndView accountManagement(Principal principal) {
		 
			ModelAndView modelAndView = new ModelAndView("MerchantAccountManagement");
			
			String name = principal.getName();
			ExternalAccount currentuser = externalAccountBO.findAccountByName(name);
			
		
			
			modelAndView.addObject("accountbyname", currentuser);
			
			return modelAndView;
		}
	 
	 @RequestMapping(value="/merchantTransferIndividual")
	 
		public synchronized ModelAndView transferToIndividual(Principal principal) {
		 
			ModelAndView modelAndView = new ModelAndView("MerchantTransferIndividual");
			
			String name = principal.getName();
			ExternalAccount currentuser = externalAccountBO.findAccountByName(name);
			
		
			
			modelAndView.addObject("accountbyname", currentuser);
			
			return modelAndView;
		}
	 
	 @RequestMapping(value="/merchantDebit")
	 
		public synchronized ModelAndView debitMoney(Principal principal) {
		 

			String name = principal.getName();
			ExternalAccount currentuser = externalAccountBO.findAccountByName(name);
		 
		 ExternalUserTransaction debit = new ExternalUserTransaction();
		 
		 debit.setUserId(currentuser.getUserid());
		 
		 debit.setAccessGranted(1);
		 
		 debit.setStatus("Pending");
		 
		 debit.setTransType("Debit");
		 
		 debit.setTransType("Debit");
			ModelAndView modelAndView = new ModelAndView("MerchantDebit");
			modelAndView.addObject("debit", debit);

			return modelAndView; 
		}
	 
	 @RequestMapping(value="/debitMerchantTransaction", method = RequestMethod.POST)
	 
		public synchronized String debitTransaction(@ModelAttribute("debit") ExternalUserTransaction externalUserTransaction, BindingResult result, ModelMap model) {
		
		 boolean res;
			String errors = "";

			if(externalUserTransaction.getAmountInvolved() < 1){
				errors = errors + "Please enter Amount greater than $1;";
			}

			res = StringValidator.inputvalidation(externalUserTransaction.getTransDetail() , "genpattern");
			if(!res){
				errors = errors + "Please enter valid Description;";
			}

			if(errors != "")
			{
				model.addAttribute("debit", externalUserTransaction);

				model.addAttribute("errors", errors);

				return "MerchantDebit";

			} else{ 
		 // Query the db and get the details of the external user 
		 
		 double amount = externalUserTransaction.getAmountInvolved();
		 
		 double currentBalance = 0.0;
		 
		 int userId = externalUserTransaction.getUserId();
		 
		 ExternalAccount externalAccount = externalAccountBO.findUserByid(userId);
			 
		 if(externalAccount != null)
		 {
		     currentBalance = externalAccount.getCurrentBalance();
		 }
		 // Check the current Balance  if not return error 
		 
		 if(currentBalance<amount)
		     {
			  return "redirect:/Merchant/MerchantLessFunds";
			 
				
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
			 
			  return "redirect:/Merchant/MerchantDebitSuccess";
			 
		 }
			}
	     
		}
	 
	 @RequestMapping(value="/MerchantLessFunds")
		
		public synchronized ModelAndView MerchantLessFunds(Principal principal) {
			
			ModelAndView modelAndView = new ModelAndView("MerchantLessFunds");
			
			ExternalAccount externalAccount =  externalAccountBO.findAccountByName(principal.getName());

			modelAndView.addObject("message", externalAccount.getCurrentBalance());

			return modelAndView;
		}

		@RequestMapping(value="/MerchantDebitSuccess")
		
		public synchronized ModelAndView MerchantDebitSuccess(Principal principal) {
			
			ModelAndView modelAndView = new ModelAndView("MerchantDebitSuccess");
			
			ExternalAccount externalAccount =  externalAccountBO.findAccountByName(principal.getName());

			modelAndView.addObject("message", externalAccount.getCurrentBalance());

			return modelAndView;
		}
	 
	 @RequestMapping(value="/merchantCredit")
	 
		public synchronized ModelAndView creditMoney(Principal principal) {
		 

			String name = principal.getName();
			ExternalAccount currentuser = externalAccountBO.findAccountByName(name);
		 
		 ExternalUserTransaction debit = new ExternalUserTransaction();
		 
		 debit.setUserId(currentuser.getUserid());
		 
		 debit.setAccessGranted(1);
		 
		 debit.setStatus("Pending");
		 
		 debit.setTransType("Credit");
		 
		 debit.setTransType("Credit");
			ModelAndView modelAndView = new ModelAndView("MerchantCredit");
			modelAndView.addObject("credit", debit);

			return modelAndView; 
		}
	 
	 @RequestMapping(value="/creditMerchantTransaction", method = RequestMethod.POST)
	 
		public synchronized String creditTransaction(@ModelAttribute("credit") ExternalUserTransaction externalUserTransaction, BindingResult result, ModelMap model) {
		
		 boolean res;
			String errors = "";

			if(externalUserTransaction.getAmountInvolved() < 1){
				errors = errors + "Please enter Amount greater than $1;";
			}

			res = StringValidator.inputvalidation(externalUserTransaction.getTransDetail() , "genpattern");
			if(!res){
				errors = errors + "Please enter valid Description;";
			}

			if(errors != "")
			{
				model.addAttribute("credit", externalUserTransaction);

				model.addAttribute("errors", errors);

				return "MerchantCredit";

			} else{
		 // Query the db and get the details of the external user 
		 
		 double amount = externalUserTransaction.getAmountInvolved();
		 
		 double currentBalance = 0.0;
		 
   int userId = externalUserTransaction.getUserId();
		 
		 ExternalAccount externalAccount = externalAccountBO.findUserByid(userId);
			 
		 if(externalAccount != null)
		 {
		     currentBalance = externalAccount.getCurrentBalance();
		     
		     currentBalance += amount;
		     
		 }

			 externalAccount.setCurrentBalance(currentBalance);
			 
			 externalAccountBO.update(externalAccount);
			 
			 // Create New Transaction
			 externalUserTransaction.setAmountInvolved(amount);
			 
			 externalTransactionBO.save(externalUserTransaction);
			 
			   return "redirect:/Merchant/MerchantCreditSuccess";
			}
				
		
		}
	 
	 @RequestMapping(value="/MerchantCreditSuccess")
		
		public synchronized ModelAndView MerchantCreditSuccess(Principal principal) {
			
			ModelAndView modelAndView = new ModelAndView("MerchantCreditSuccess");
			
			ExternalAccount externalAccount =  externalAccountBO.findAccountByName(principal.getName());

			modelAndView.addObject("message", externalAccount.getCurrentBalance());

			return modelAndView;
		}
	 
	 @RequestMapping(value="/merchantTransfer")
	 
		public synchronized ModelAndView transferMoney(Principal principal) {
		 
		 String name = principal.getName();
			ExternalAccount currentuser = externalAccountBO.findAccountByName(name);
		 
		 ExternalUserTransaction transfer = new ExternalUserTransaction();
		 
		 transfer.setUserId(currentuser.getUserid());
		 
		 transfer.setAccessGranted(1);
		 
		 transfer.setStatus("Pending");
		 
		 transfer.setTransType("Transfer");
			ModelAndView modelAndView = new ModelAndView("MerchantTransfer");
			modelAndView.addObject("transfer", transfer);

			return modelAndView; 
		}
	 
	
	 
	 @RequestMapping(value="/merchantTransferTransaction", method = RequestMethod.POST)

		public synchronized String transferTransaction(@ModelAttribute("transfer") ExternalUserTransaction externalUserTransaction, BindingResult result, ModelMap model) 
		{

			boolean res;
			String errors = "";

			if(externalUserTransaction.getAmountInvolved() < 1){
				errors = errors + "Please enter Amount greater than $1;";
			}

			res = StringValidator.inputvalidation(externalUserTransaction.getTransDetail() , "onlynumbers");
			if(!res){
				errors = errors + "Please enter valid Account Number;";
			}

			if(errors != "")
			{
				model.addAttribute("transfer", externalUserTransaction);

				model.addAttribute("errors", errors);

				return "MerchantTransfer";

			} else{
				// Query the db and get the details of the external user 

				double amountToTransfer = externalUserTransaction.getAmountInvolved();

				double currentSourceBalance = 0.0;

				int userId = externalUserTransaction.getUserId();

				ExternalAccount sourceExternalAccount = externalAccountBO.findUserByid(userId);

				if(sourceExternalAccount != null)
				{
					currentSourceBalance = sourceExternalAccount.getCurrentBalance();
				}
				// Check the current Balance  if not return error 

				if(currentSourceBalance<amountToTransfer)
				{
					return "redirect:/Merchant/MerchantLessFunds";


				}

				//else process the transaction.
				else
				{

					// Find the account from database where money needs to be transfered.

					String accountNumber = externalUserTransaction.getTransDetail();

					ExternalAccount destinationExternalAccount = merchantsBO.getAccountByAccountNumber(accountNumber);

					if(destinationExternalAccount == null || (sourceExternalAccount.getAccountNo().equals(destinationExternalAccount.getAccountNo())))
					{
						errors = errors + "Please enter valid Account Number;";
						model.addAttribute("transfer", externalUserTransaction);

						model.addAttribute("errors", errors);

						return "MerchantTransfer";


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

					return "redirect:/Merchant/MerchantTransferSuccess";


				}
			}

		}
	 
	 @RequestMapping(value="/MerchantTransferIncorrectAccountNumber")
		
		public synchronized ModelAndView MerchantTransferIncorrectAccountNumber(Principal principal) {
			
			ModelAndView modelAndView = new ModelAndView("MerchantTransferIncorrectAccountNumber");
			


			return modelAndView;
		}


	@RequestMapping(value="/MerchantTransferSuccess")

	public synchronized ModelAndView MerchantTransferSuccess(Principal principal) {
		
		ModelAndView modelAndView = new ModelAndView("MerchantTransferSuccess");
		
		ExternalAccount externalAccount =  externalAccountBO.findAccountByName(principal.getName());

		modelAndView.addObject("message", externalAccount.getCurrentBalance());

		return modelAndView;
	}
	 
	 
	 @RequestMapping(value="/merchantCreditCardRequest")
	 
		public synchronized ModelAndView creditCardRequest(Principal principal) {
		 
		 String name = principal.getName();
			ExternalAccount currentuser = externalAccountBO.findAccountByName(name);
		 
      
      // Query the credit card request stable to find the request for the account number 
      CreditcardRequests creditCardCheck = ccreqsBo.checkForCreditCardNumber(currentuser.getAccountNo());
      
     //if user populate with row
      if(creditCardCheck != null)
      {
     	 ModelAndView modelAndView = new ModelAndView("MerchantCreditCardRequest");
 	     
			  modelAndView.addObject("creditCard", creditCardCheck);
		 
	         return modelAndView;
      }
      //If no credit new request and add to credit card request
      else{
     	 
     	 
          CreditcardRequests creditCardNewRequest = new CreditcardRequests();
 		 
          creditCardNewRequest.setApproved("no");
 	     
          creditCardNewRequest.setAccountNo(currentuser.getAccountNo());
 	     
 	     ccreqsBo.save(creditCardNewRequest);
 	     
 	     ModelAndView modelAndView = new ModelAndView("MerchantCreditCardRequest");
 	     
	     modelAndView.addObject("creditCard", creditCardNewRequest);
 		 
 	     return modelAndView;
     	 
      }
  }
	 
	 
		@RequestMapping(value="/makeCustomerPayment")

		public synchronized ModelAndView MerchantUploadFileUI() {
			
			FileUpload fileUpload = new FileUpload();
			
			ModelAndView modelAndView = new ModelAndView("UploadFileUI");
			
			modelAndView.addObject("fileUploadForm",fileUpload);

			return modelAndView;
		}
	 
		@RequestMapping(value="/FileUploaded")

         public synchronized ModelAndView MerchantFileUploaded(@ModelAttribute("fileUploadForm") FileUpload fileUploaded) {
			
			MultipartFile multipartFile = fileUploaded.getFile();
			
  		   ModelAndView modelAndView  = null;
  		   
			String fileName="";
	 
			if(multipartFile!=null){
				fileName = multipartFile.getOriginalFilename();
				
				// Extract File Name 
				// fileName = Certificate_2941130.cert
						 
			     fileName= fileName.substring(12,19);
			     
				
				//Query the Database and get the blob
			     
				    Pki publicKey =  pkiBO.findPKI(fileName);
				
				    
				//Verifying THe certificate using public key 
					CertificateFactory cf = null;
					try {
						cf = CertificateFactory.getInstance("X.509");
					} catch (CertificateException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
		        	X509Certificate cert = null;
					try {
						cert = (X509Certificate)cf.generateCertificate(multipartFile.getInputStream());
					} catch (CertificateException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        	
		        	 try{ 
		                 String algorithm = "RSA";
		                 KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		                 EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKey.getCert().getBytes(1,(int) publicKey.getCert().length() ));
		                 PublicKey regenerateThePublicKey = keyFactory.generatePublic(publicKeySpec);
		                 cert.verify(regenerateThePublicKey);	
		                 
		                 //get the merchant name 
		                   if(publicKey!=null)
		                   {
		                	   String merchantName = publicKey.getMerchant();
		                	   
		                	   if(!merchantName.isEmpty())
		                	   {
		                		// query the database get merchant record
		                		   
		                		  ExternalAccount merchant =  externalAccountBO.findAccountByName(merchantName);
		                		   
		                		   double currentBalance = merchant.getCurrentBalance();
		                		   
		                		   currentBalance = currentBalance + publicKey.getAmountInvolved();
		                		   
		                		   merchant.setCurrentBalance(currentBalance);
		                				   
		                		   externalAccountBO.update(merchant);
		                		   
		                		    modelAndView = new ModelAndView("MerchantFileUploadSuccess");
				                	 
				                	 modelAndView.addObject("message",currentBalance);
				                	 
				                	 pkiBO.delete(publicKey);
				                	 
				               
				                	// Create an External Transaction 
				                   	 
				                   	 ExternalUserTransaction external = new ExternalUserTransaction();
				                   	 
				                   	 external.setAccessGranted(1);
				                   	 external.setAmountInvolved(publicKey.getAmountInvolved());
				                   	 external.setStatus("Approved");
				                   	 external.setTransDetail(publicKey.getTransDetail());
				                   	 external.setTransType("Debit");
				                   	 external.setUserId(merchant.getUserid());
				                   	externalTransactionBO.save(external);

		                	   }
		                   }
		                  
		                 
		                 // Update the merchant balance with the amount
		                 
		                 }catch(Exception e)
		                 {
		                 	//has to go the a page to say that certificate didnt match.
		                	 
		             		return new ModelAndView("MerchantCertificateValidationFailed");
		                 }
				
			}
			
	 
			return  modelAndView;
		}
	 
}
