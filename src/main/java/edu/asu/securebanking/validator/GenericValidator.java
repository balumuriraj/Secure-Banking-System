package edu.asu.securebanking.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.securebanking.model.PassKey;

@Component
public class GenericValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		System.out.println("enetered boolean!");
		return PassKey.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		System.out.println("enetered validate!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passKey", "required.passKey", "passKey is required.");
		
		PassKey pkey = (PassKey)target;
		Boolean result;
		String input = pkey.getPassKey();
		System.out.println("passkey:"+pkey.getPassKey());
		
		
		result = StringValidator.inputvalidation(input, "address");
		System.out.println("completed String validate!"+result);
		if(!result){
			errors.rejectValue("passKey","regex.passkey");
		}
		
		System.out.println("completed validate!");
		
		
	}

}
