package edu.asu.securebanking.businessobjectImpl;

import edu.asu.securebanking.businessobject.CreateInternalAccountBO;
import edu.asu.securebanking.businessobject.OTPBO;
import edu.asu.securebanking.dataaccessobject.CreateInternalAccountDao;
import edu.asu.securebanking.dataaccessobject.OTPDao;
import edu.asu.securebanking.model.ExternalAccount;
import edu.asu.securebanking.model.InternalAccount;
import edu.asu.securebanking.model.OTP;


public class OTPBOImpl implements OTPBO {

	OTPDao OTPDao;
	 
	public void setOTPDao (OTPDao  OTPDao) {
		this.OTPDao = OTPDao;
	}

	@Override
	public void save(OTP OTP) {
		// TODO Auto-generated method stub
		OTPDao.save(OTP);
	}

	@Override
	public void update(OTP OTP) {
		// TODO Auto-generated method stub
		OTPDao.update(OTP);
	}

	@Override
	public void delete(OTP OTP) {
		// TODO Auto-generated method stub
		OTPDao.delete(OTP);
	}

	@Override
	public OTP findAccountByName(int userid, int empid) {
		// TODO Auto-generated method stub
		OTP result = OTPDao.findAccountByNameExternal(userid, empid);

		if(result == null){
			return null;
		}

		return result;
	}



}
