package edu.asu.securebanking.dataaccessobject;


import edu.asu.securebanking.model.OTP;

public interface OTPDao {
		void save(OTP OTP);
		void update(OTP OTP);
		void delete(OTP OTP);
		OTP findAccountByNameExternal(int userid, int empid);

}
