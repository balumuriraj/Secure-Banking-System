package edu.asu.securebanking.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.securebanking.model.ExternalAccount;

@Component
public class ExternalUserValidator  implements Validator{
	
	@Override 
	public boolean supports(Class<?> clazz) {
		//just validate the Customer instances
		return ExternalAccount.class.isAssignableFrom(clazz);
	}
 
	@Override 
	public void validate(Object target, Errors errors) {
 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "required.username", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required.password", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vpassword", "required.vpassword", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "required.firstname", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "required.lastname", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ssn", "required.ssn", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dob", "required.dob", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "required.address", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required.email", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telephone", "required.telephone", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "required.gender", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "securityquestion", "required.securityquestion", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "securityanswer", "required.securityanswer","Field name is required.");
		
		ExternalAccount user = (ExternalAccount)target;
		Boolean result;
		
		result = StringValidator.inputvalidation(user.getUsername(), "username");
		if(!result){
			errors.rejectValue("username","regex.username");
		}
		
		result = StringValidator.inputvalidation(user.getPassword(), "password");
		if(!result){
			errors.rejectValue("password","regex.password");
		}
		
		result = StringValidator.inputvalidation(user.getFirstname(), "general");
		if(!result){
			errors.rejectValue("firstname","regex.general");
		}
		
		result = StringValidator.inputvalidation(user.getLastname(), "general");
		if(!result){
			errors.rejectValue("lastname","regex.general");
		}
		
		result = StringValidator.inputvalidation(user.getDob(), "date");
		if(!result){
			errors.rejectValue("dob","regex.date");
		}
		
		result = StringValidator.inputvalidation(user.getSecurityanswer(), "general");
		if(!result){
			errors.rejectValue("securityanswer","regex.general");
		}
		
		result = StringValidator.inputvalidation(user.getAddress(), "address");
		if(!result){
			errors.rejectValue("address","regex.address");
		}
		
		result = StringValidator.inputvalidation(user.getSsn(), "number");
		if(!result){
			errors.rejectValue("ssn","regex.number");
		}
		
		result = StringValidator.inputvalidation(user.getTelephone(), "number");
		if(!result){
			errors.rejectValue("telephone","regex.number");
		}
		
		result = StringValidator.inputvalidation(user.getEmail(), "email");
		if(!result){
			errors.rejectValue("email","regex.email");
		}
		
 
		if(!(user.getPassword().equals(user.getVpassword()))){
			errors.rejectValue("password", "notmatch.password");
		}

		if("NONE".equals(user.getSecurityquestion())){
			errors.rejectValue("securityquestion", "required.securityquestion");
		}
	}

}
