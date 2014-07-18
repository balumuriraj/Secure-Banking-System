package edu.asu.securebanking.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Principal;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Blob;

import edu.asu.securebanking.businessobject.PkiBO;
import edu.asu.securebanking.model.Pki;

@Controller
@SessionAttributes
@RequestMapping("/pki")
public class PkiController {

	@Autowired 
	PkiBO PkiBO;
	
	static X509Certificate certificate; 
	
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model) 
	{
		Pki pki = new Pki();
		model.addAttribute("pkisubmit",pki);
		return "pkiform";
	}
	
	@RequestMapping(value="/pkiform",method = RequestMethod.POST)
	public synchronized ModelAndView submit(@ModelAttribute("pkisubmit") Pki pki,Principal principal)
	{
		ModelAndView modelAndView;
		KeyPair keyPair = null;
		try {
			keyPair = generateCertificate(principal.getName());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   
		
		pki.setUserName("aniket");
	    //get a blob out of the keypair
	    Blob blob = PkiBO.createKeyBlob(keyPair);
	    
		//pki.setCert(null);
	    pki.setCert(blob);
	    PkiBO.save(pki);
	    
	    //mail
	    sendMail(principal.getName());
	    
	    try
	    {
	    	certificate.verify(keyPair.getPublic());
	    	System.out.println("Verified");
	    	modelAndView = new ModelAndView("redirect:/SecurityApplication/pkiform");
	    	return modelAndView;
	    }
	    catch(Exception e)
	    {
	    	System.out.println("private");
	    	modelAndView = new ModelAndView("redirect:/error2.jsp");
	    	return modelAndView;
	    }
	}
	
	public synchronized KeyPair generateCertificate(String username) throws Exception
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
		
		File file = new File(System.getProperty("catalina.home") + "\\logs\\UserCertificate"+username+".cert");
		FileOutputStream fos = new FileOutputStream(file);  
	    fos.write(certificate.getEncoded());
	    fos.close();	   
	    
	    return keyPair;
	}
	
	public synchronized void sendMail(String username)
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
						InternetAddress.parse("appatil@asu.edu"));
				message.setSubject("Certificate for Sun Devil Bank of Arizona State (SDBAS)");
				message.setText("Dear our valued customer, " + username +
						"\n\n The certificate attached below will be used by you when making any payments on behalf of customer");

		        MimeBodyPart messageBodyPart = new MimeBodyPart();

		        Multipart multipart = new MimeMultipart();

		        messageBodyPart = new MimeBodyPart();
		        String file = System.getProperty("catalina.home") + "\\logs\\UserCertificate"+username+".cert";
		        String fileName = "Certificate_"+username+".cert";
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
}
