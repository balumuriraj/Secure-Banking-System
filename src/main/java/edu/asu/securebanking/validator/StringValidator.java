package edu.asu.securebanking.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringValidator {
	private static final String pwdpattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	private static final String usrpattern = "(^[a-z]{6,20}$)";
	private static final String numpattern = "(^[0-9]{10}$)";
	private static final String onlynumpattern = "(^([0-9])+$)";
	private static final String floatnumpattern = "(^[0-9.]+$)";
	private static final String deptpattern = "(^[1-6]*$)";
	private static final String addrpattern = "(^[a-zA-Z0-9]{0,50}+$)";
	private static final String genpattern = "(^[a-zA-Z ]{0,20}+$)";
	private static final String emailpattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String datepattern = "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)";
	
	public static boolean inputvalidation(final String input, String type) {
		System.out.println("entering string  validate!");
		String pattern;
		if(type == "password"){
			pattern = pwdpattern;
		}
		else if(type == "username"){
			pattern = usrpattern;
		}
		else if(type == "number"){
			pattern = numpattern;
		}
		else if(type == "onlynumbers"){
			pattern = onlynumpattern;
		}
		else if(type == "floatnumbers"){
			pattern = floatnumpattern;
		}
		else if(type == "dept"){
			pattern = deptpattern;
		}
		else if(type == "email"){
			pattern = emailpattern;
		}
		else if(type == "date"){
			pattern = datepattern;
		}
		else if(type == "address"){
			System.out.println("pattern address!");
			pattern = addrpattern;
		}
		else{
			pattern = genpattern;
		}
		System.out.println("exiting string  validate!");
		return Pattern.compile(pattern).matcher(input).matches();
	}

}

/*(			# Start of group
		  (?=.*\d)		#   must contains one digit from 0-9
		  (?=.*[a-z])		#   must contains one lowercase characters
		  (?=.*[A-Z])		#   must contains one uppercase characters
		  (?=.*[@#$%])		#   must contains one special symbols in the list "@#$%"
              .		#     match anything with previous condition checking
                {6,20}	#        length at least 6 characters and maximum of 20	
)			# End of group*/