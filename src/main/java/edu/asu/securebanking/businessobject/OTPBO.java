package edu.asu.securebanking.businessobject;

import edu.asu.securebanking.model.OTP;

public interface OTPBO {
		void save(OTP OTP);
		void update(OTP OTP);
		void delete(OTP OTP);
		OTP findAccountByName(int userid, int empid);
}
