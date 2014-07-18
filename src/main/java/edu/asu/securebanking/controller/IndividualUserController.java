package edu.asu.securebanking.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Principal;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.security.auth.x500.X500Principal;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;
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

import com.mysql.jdbc.Blob;

import edu.asu.securebanking.businessobject.CreateExternalAccountBO;
import edu.asu.securebanking.businessobject.CreditcardRequestsBO;
import edu.asu.securebanking.businessobject.ExternalUserTransactionBO;
import edu.asu.securebanking.businessobject.IndividualUserBO;
import edu.asu.securebanking.businessobject.PkiBO;
import edu.asu.securebanking.model.CreditcardRequests;
import edu.asu.securebanking.model.ExternalAccount;
import edu.asu.securebanking.model.ExternalUserTransaction;
import edu.asu.securebanking.model.FileUpload;
import edu.asu.securebanking.model.Pki;
import edu.asu.securebanking.validator.StringValidator;

@Controller
@SessionAttributes
@RequestMapping("/IndividualUser")
public class IndividualUserController {

	@Autowired
	CreateExternalAccountBO extBo;

	@Autowired
	IndividualUserBO individualUserBO;

	@Autowired
	CreateExternalAccountBO externalAccountBO;

	@Autowired
	ExternalUserTransactionBO externalTransactionBO;

	@Autowired
	CreditcardRequestsBO ccreqsBo;

	@Autowired
	PkiBO pkiBO;
	
	static X509Certificate certificate; 
	
	/*@RequestMapping(method = RequestMethod.GET)
	public ModelAndView initForm(ModelMap model){

		//return form view
		return new ModelAndView("IndividualUserHomePage","indiUser", new ExternalAccount());
	}*/

	@RequestMapping(value="/viewTransactions")

	public synchronized ModelAndView listOflTransactions(Principal principal) {

		ModelAndView modelAndView = new ModelAndView("IndividualUserTransactions");

		String name = principal.getName();
		ExternalAccount currentuser = extBo.findAccountByName(name);

		List<ExternalUserTransaction> transactions = individualUserBO.getAllUserTransactions(currentuser.getUserid());

		modelAndView.addObject("transactions", transactions);

		return modelAndView;
	}



	@RequestMapping(value="/accountManagement")
	public synchronized ModelAndView accountManagement(Principal principal) {
		System.out.println("step0");
		String name = principal.getName();
		System.out.println(name);
		ModelAndView modelAndView = new ModelAndView("InternalUserAccountManagementHomePage");
		System.out.println("step1");
		ExternalAccount currentuser = extBo.findAccountByName(name);
		System.out.println(name);

		modelAndView.addObject("accountbyname", currentuser);

		return modelAndView;
	}



	@RequestMapping(value="/debit")

	public synchronized ModelAndView debitMoney(Principal principal) {

		String name = principal.getName();
		ExternalAccount currentuser = extBo.findAccountByName(name);

		ExternalUserTransaction debit = new ExternalUserTransaction();

		debit.setUserId(currentuser.getUserid());

		debit.setAccessGranted(1);

		debit.setStatus("Pending");

		debit.setTransType("Debit");
		ModelAndView modelAndView = new ModelAndView("IndividualUserDebit");
		modelAndView.addObject("debit", debit);

		return modelAndView; 
	}

	@RequestMapping(value="/debitTransaction", method = RequestMethod.POST)

	public synchronized String debitTransaction(@ModelAttribute("debit") ExternalUserTransaction externalUserTransaction, BindingResult result,Principal principal, ModelMap model) {

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

			return "IndividualUserDebit";

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
	
			errors = errors + "No sufficient balance;";

			model.addAttribute("debit", externalUserTransaction);

			model.addAttribute("errors", errors);

			return "IndividualUserDebit";

			//return modelAndView;
		}

		//else process the transaction.
		else
		{
			//Update the current Balance 
			
			
			amount=amount*-1;
			
			if((amount*-1)>1000)
			{
				
				
				long randomNumber = (long) Math.floor(Math.random() * 9000000) + 1000000L;
				
				// GENERATE CERTIFICATE FOR THE TRANSACTION
				KeyPair keyPair = null;
				try {
					keyPair = generateCertificate(principal.getName(),randomNumber);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			   
				Pki pki = new Pki();
				pki.setUserName(principal.getName());
				pki.setTransId(Long.toString(randomNumber));
				pki.setMerchant("");
				pki.setAmountInvolved(amount);
				pki.setTransDetail("Transfer From Individual " + principal.getName());
			    //get a blob out of the keypair
			    Blob blob = pkiBO.createKeyBlob(keyPair);
			    
				//UPDATING THE ENTRY IN DATABASE
			    pki.setCert(blob);
			    pkiBO.save(pki);
			    
			    //EMAILING THE MERCHANT
			    sendMail(principal.getName()," ","Transfer From Individual " ,Long.toString(randomNumber),amount,externalAccount.getEmail());

				return "redirect:/IndividualUser/FileUpload";
				
			}

			currentBalance =  currentBalance + amount;

			externalAccount.setCurrentBalance(currentBalance);

			externalAccountBO.update(externalAccount);

			// Create New Transaction
			externalUserTransaction.setAmountInvolved(amount);

			externalTransactionBO.save(externalUserTransaction);

			ModelAndView modelAndView = new ModelAndView("IndividualUserDebitSuccess");

			modelAndView.addObject("message", currentBalance);

			//return modelAndView;
			
			
			return "redirect:/IndividualUser/IndividualUserDebitSuccess";
			
		

		}
		}

	}


	
	@RequestMapping(value="/FileUpload")

	public synchronized ModelAndView FileUpload() {
		
		FileUpload fileUpload = new FileUpload();
		
		ModelAndView modelAndView = new ModelAndView("IndividualUploadFileUI");
		
		modelAndView.addObject("fileUploadForm",fileUpload);

		return modelAndView;
	}

	@RequestMapping(value="/FileUploaded")

    public synchronized ModelAndView IndividualFileUploaded(@ModelAttribute("fileUploadForm") FileUpload fileUploaded) {
		
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
	                	   String userName = publicKey.getUserName();
	                	   
	                	   if(!userName.isEmpty())
	                	   {
	                		// query the database get merchant record
	                		   
	                		  ExternalAccount user =  externalAccountBO.findAccountByName(userName);
	                		   
	                		   double currentBalance = user.getCurrentBalance();
	                		   
	                		   currentBalance = currentBalance + publicKey.getAmountInvolved();
	                		   
	                		   user.setCurrentBalance(currentBalance);
	                				   
	                		   externalAccountBO.update(user);
	                		   
	                		    modelAndView = new ModelAndView("IndividualFileUploadSuccess");
			                	 
			                	 modelAndView.addObject("message",currentBalance);
			                	 
			                	 // Create an External Transaction 
			                	 
			                	 ExternalUserTransaction external = new ExternalUserTransaction();
			                	 
			                	 external.setAccessGranted(1);
			                	 external.setAmountInvolved(publicKey.getAmountInvolved());
			                	 external.setStatus("Approved");
			                	 external.setTransDetail(publicKey.getTransDetail());
			                	 external.setTransType("Debit");
			                	 external.setUserId(user.getUserid());
			                	 externalTransactionBO.save(external);
			                	 pkiBO.delete(publicKey);
			                	 
			               

	                	   }
	                   }
	                  
	                 
	                 // Update the merchant balance with the amount
	                 
	                 }catch(Exception e)
	                 {
	                 	//has to go the a page to say that certificate didnt match.
	                	 
	             		return new ModelAndView("IndividualCertificateValidationFailed");
	                 }
			
		}
		

		return  modelAndView;
	}

@RequestMapping(value="/IndividualUserLessFunds")
	
	public synchronized ModelAndView IndividualUserLessFunds(Principal principal) {
		
		ModelAndView modelAndView = new ModelAndView("IndividualUserLessFunds");
		
		ExternalAccount externalAccount =  externalAccountBO.findAccountByName(principal.getName());

		modelAndView.addObject("message", externalAccount.getCurrentBalance());

		return modelAndView;
	}

	@RequestMapping(value="/IndividualUserDebitSuccess")
	
	public synchronized ModelAndView IndividualUserDebitSuccess(Principal principal) {
		
		ModelAndView modelAndView = new ModelAndView("IndividualUserDebitSuccess");
		
		ExternalAccount externalAccount =  externalAccountBO.findAccountByName(principal.getName());

		modelAndView.addObject("message", externalAccount.getCurrentBalance());

		return modelAndView;
	}
	
	@RequestMapping(value="/credit")

	public synchronized ModelAndView creditMoney(Principal principal) {

		String name = principal.getName();
		ExternalAccount currentuser = extBo.findAccountByName(name);

		ExternalUserTransaction credit = new ExternalUserTransaction();

		credit.setUserId(currentuser.getUserid());

		credit.setAccessGranted(1);

		credit.setStatus("Pending");

		credit.setTransType("Credit");

		ModelAndView modelAndView = new ModelAndView("IndividualUserCredit");
		modelAndView.addObject("credit", credit);

		return modelAndView; 
	}
	
	@RequestMapping(value="/creditTransaction", method = RequestMethod.POST)

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

			return "IndividualUserCredit";

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

		ModelAndView modelAndView = new ModelAndView("IndividualUserCreditSuccess");

		modelAndView.addObject("message", currentBalance);

		return "redirect:/IndividualUser/IndividualUserCreditSuccess";
		}



	}
	
@RequestMapping(value="/IndividualUserCreditSuccess")
	
	public synchronized ModelAndView IndividualUserCreditSuccess(Principal principal) {
		
		ModelAndView modelAndView = new ModelAndView("IndividualUserCreditSuccess");
		
		ExternalAccount externalAccount =  externalAccountBO.findAccountByName(principal.getName());

		modelAndView.addObject("message", externalAccount.getCurrentBalance());

		return modelAndView;
	}
	
	
	

	@RequestMapping(value="/transfer")

	public synchronized ModelAndView transferMoney(Principal principal) {

		String name = principal.getName();
		ExternalAccount currentuser = extBo.findAccountByName(name);

		ExternalUserTransaction transfer = new ExternalUserTransaction();

		transfer.setUserId(currentuser.getUserid());

		transfer.setAccessGranted(1);

		transfer.setStatus("Pending");

		transfer.setTransType("Transfer");

		ModelAndView modelAndView = new ModelAndView("IndividualUserTransfer");
		modelAndView.addObject("transfer", transfer);
		return modelAndView;
	}
	
	
	@RequestMapping(value="/transferToMerchant")
	public synchronized ModelAndView transferToMerchant(Principal principal) {

		String name = principal.getName();
		ExternalAccount currentuser = extBo.findAccountByName(name);
		ExternalUserTransaction transfer = new ExternalUserTransaction();

		transfer.setUserId(currentuser.getUserid());

		transfer.setAccessGranted(1);

		transfer.setStatus("Pending");

		transfer.setTransType("debitToMerchant");
		
		ModelAndView modelAndView = new ModelAndView("IndividualUserTransferToMerchant");
		modelAndView.addObject("transfer", transfer);
		return modelAndView;
	}

	@RequestMapping(value="MerchantUserNameIncorrect")
	public String MerchantUserNameIncorrect()
	{
		return "MerchantUserNameIncorrect";
	}
	
	@RequestMapping(value="/transferMerchantTransaction", method = RequestMethod.POST)

	public synchronized String transferMerchantTransaction(@ModelAttribute("transfer") ExternalUserTransaction externalUserTransaction, BindingResult result,Principal principal, ModelMap model) 
	{
		boolean res;
		String errors = "";
		

		res = StringValidator.inputvalidation(externalUserTransaction.getTransDetail(), "username");
		if(!res){
			errors = errors + "Please enter valid username;";
		}
		
		if(externalUserTransaction.getAmountInvolved() < 1){
			errors = errors + "Please enter Amount greater than $1;";
		}


		if(errors != "")
		{
			

			model.addAttribute("errors", errors);
			model.addAttribute("transfer", externalUserTransaction);
	
				return "IndividualUserTransferToMerchant";

		} else{

		String merchantUserName = externalUserTransaction.getTransDetail();
		
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
			return "redirect:/IndividualUser/IndividualUserLessFunds";
		}

		//else process the transaction.
		else
		{

			// Find the account from database where money needs to be transfered.
			
			ExternalAccount destinationExternalAccount = individualUserBO.getAccountByUsername(merchantUserName);

			if(destinationExternalAccount == null)
			{
				return "redirect:/IndividualUser/MerchantUserNameIncorrect";

			}
			else if(sourceExternalAccount.getUsername().equals(destinationExternalAccount.getUsername()))
			{
				return "redirect:/IndividualUser/MerchantUserNameIncorrect";
			}

			// Update Source Account Balance 
			sourceExternalAccount.setCurrentBalance(currentSourceBalance-amountToTransfer);

			externalAccountBO.update(sourceExternalAccount);

			// Create Transaction For Source Account 

			externalUserTransaction.setTransDetail("Transfer To Merchant " + merchantUserName);

			externalUserTransaction.setAmountInvolved(amountToTransfer*-1);

			externalTransactionBO.save(externalUserTransaction);


			//Create Transaction for Destination Balance 		

/*			externalUserTransaction.setUserId(destinationExternalAccount.getUserid());

			externalUserTransaction.setTransType("debitFromUser");
			externalUserTransaction.setTransDetail("Transfer From Individual " + sourceExternalAccount.getUsername());

			externalUserTransaction.setAmountInvolved(amountToTransfer);

			externalTransactionBO.save(externalUserTransaction);*/
			
			long randomNumber = (long) Math.floor(Math.random() * 9000000) + 1000000L;
			
			// GENERATE CERTIFICATE FOR THE TRANSACTION
			KeyPair keyPair = null;
			try {
				keyPair = generateCertificate(principal.getName(),randomNumber);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		   
			Pki pki = new Pki();
			pki.setUserName(principal.getName());
			pki.setTransId(Long.toString(randomNumber));
			pki.setMerchant(merchantUserName);
			pki.setAmountInvolved(amountToTransfer);
			pki.setTransDetail("Transfer From Individual " + sourceExternalAccount.getUsername());
		    //get a blob out of the keypair
		    Blob blob = pkiBO.createKeyBlob(keyPair);
		    
			//UPDATING THE ENTRY IN DATABASE
		    pki.setCert(blob);
		    pkiBO.save(pki);
		    
		    //EMAILING THE MERCHANT
		    sendMail(merchantUserName,principal.getName(),"Transfer From Individual " + sourceExternalAccount.getUsername(),Long.toString(randomNumber),amountToTransfer,destinationExternalAccount.getEmail());

		 
			return "redirect:/IndividualUser/MerchantUserTransferSuccess";

		}
		}

	}


	@RequestMapping(value="/transferTransaction", method = RequestMethod.POST)

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

			return "IndividualUserTransfer";

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
				return "redirect:/IndividualUser/IndividualUserCreditSuccess";
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
					model.addAttribute("transfer", externalUserTransaction);

					model.addAttribute("errors", errors);

					return "IndividualUserTransfer";

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


				return "redirect:/IndividualUser/IndividualUserTransferSuccess";

			}
		}

	}

@RequestMapping(value="/IndividualUserTransferSuccess")
	
	public synchronized ModelAndView IndividualUserTransferSuccess(Principal principal) {
		
		ModelAndView modelAndView = new ModelAndView("IndividualUserTransferSuccess");
		
		ExternalAccount externalAccount =  externalAccountBO.findAccountByName(principal.getName());

		modelAndView.addObject("message", externalAccount.getCurrentBalance());

		return modelAndView;
	}

@RequestMapping(value="/MerchantUserTransferSuccess")

public synchronized ModelAndView MerchantUserTransferSuccess(Principal principal) {
	
	ModelAndView modelAndView = new ModelAndView("MerchantUserTransferSuccess");
	
	ExternalAccount externalAccount =  externalAccountBO.findAccountByName(principal.getName());

	modelAndView.addObject("message", externalAccount.getCurrentBalance());

	return modelAndView;
}


@RequestMapping(value="/IndividualUserTransferIncorrectAccountNumber")

public ModelAndView IndividualUserTransferIncorrectAccountNumber(Principal principal) {
	
	ModelAndView modelAndView = new ModelAndView("IndividualUserTransferIncorrectAccountNumber");

	return modelAndView;
}


	@RequestMapping(value="/creditCardRequest")

	public synchronized ModelAndView creditCardRequest(Principal principal) {

		String name = principal.getName();
		ExternalAccount currentuser = extBo.findAccountByName(name);

		// Query the credit card request stable to find the request for the account number 
		CreditcardRequests creditCardCheck = ccreqsBo.checkForCreditCardNumber(currentuser.getAccountNo());

		//if user populate with row
		if(creditCardCheck != null)
		{
			ModelAndView modelAndView = new ModelAndView("IndividualUserCreditCardRequest");

			modelAndView.addObject("creditCard", creditCardCheck);

			return modelAndView;
		}
		//If no credit new request and add to credit card request
		else{


			CreditcardRequests creditCardNewRequest = new CreditcardRequests();

			creditCardNewRequest.setApproved("no");

			creditCardNewRequest.setAccountNo(currentuser.getAccountNo());

			ccreqsBo.save(creditCardNewRequest);

			ModelAndView modelAndView = new ModelAndView("IndividualUserCreditCardRequest");

			modelAndView.addObject("creditCard", creditCardNewRequest);

			return modelAndView;

		}


	}

	public synchronized KeyPair generateCertificate(String username, long transID) throws Exception
	{
		Security.addProvider(new BouncyCastleProvider());
		// yesterday
	    Date validityBeginDate = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
	    // in 1 years
	    Date validityExpireDate = new Date(System.currentTimeMillis() + 365* 24 * 60 * 60 * 1000);
	    
	    // KEY GENERATION
	    KeyFactory fact = KeyFactory.getInstance("RSA");
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
	    keyPairGenerator.initialize(1024, new SecureRandom());
	    KeyPair keyPair = keyPairGenerator.generateKeyPair();
	    KeyPair keyPair1 = keyPairGenerator.generateKeyPair();
	    
	    X509V3CertificateGenerator certGenerator = new X509V3CertificateGenerator();
		X500Principal certName = new X500Principal("CN="+"test");
		
		certGenerator.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
		certGenerator.setSubjectDN(certName);
		certGenerator.setIssuerDN(certName); // use the same
		certGenerator.setNotBefore(validityBeginDate);
		certGenerator.setNotAfter(validityExpireDate);
		certGenerator.setPublicKey(keyPair.getPublic());
		certGenerator.setSignatureAlgorithm("SHA256WithRSAEncryption");
		
		certificate = certGenerator.generate(keyPair.getPrivate(), "BC");
		
		File file = new File(System.getProperty("catalina.home") + "\\logs\\Certificate_"+transID+".cert");
		FileOutputStream fos = new FileOutputStream(file);  
	    fos.write(certificate.getEncoded());
	    fos.close();	   
	    
	    return keyPair;
	}
	
	public void sendMail(String merchant,String user,String transDetail,String transID,double amountInvolved,String email)
	{
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
						InternetAddress.parse(email));
				message.setSubject("Certificate for Sun Devil Bank of Arizona State (SDBAS)");
	
				String text = "Dear our valued Merchant " + merchant +
						" Customer "+user+ " made a payment as a debit to you"+
						" Transaction ID : "+transID+
						" Amount Involved : "+amountInvolved+
						" Transaction Detail : "+transDetail+
						" Please use the certificate attached below to claim the amount by going to Make Customer Payment after you log in";

			    BodyPart messageBodyPart = new MimeBodyPart();
	            // Fill the message
				messageBodyPart.setText(text);
				// Create a multipar message
				Multipart multipart = new MimeMultipart();
				// Set text message part
				multipart.addBodyPart(messageBodyPart);
				
				
				// Part two is attachment
				messageBodyPart = new MimeBodyPart();		
				
				String file = System.getProperty("catalina.home") + "\\logs\\Certificate_"+transID+".cert";
			    String fileName = "Certificate_"+transID+".cert";
						
		        DataSource source = new FileDataSource(file);
		        messageBodyPart.setDataHandler(new DataHandler(source));
		        messageBodyPart.setFileName(fileName);
		        multipart.addBodyPart(messageBodyPart);

		        message.setContent(multipart);
		       

		        System.out.println("Sending");

		        Transport.send(message);

		        System.out.println("Done");

		    } catch (MessagingException e) {
		        e.printStackTrace();
		    }
	}
	
	
	
	@RequestMapping(value="/RequestCertificate")

	public synchronized ModelAndView RequestCertificate(Principal principal) {
		
		ModelAndView modelAndView = new ModelAndView("MerchantUserTransferSuccess");
		
		ExternalAccount externalAccount =  externalAccountBO.findAccountByName(principal.getName());

		modelAndView.addObject("message", externalAccount.getCurrentBalance());

		return modelAndView;
	}


}
