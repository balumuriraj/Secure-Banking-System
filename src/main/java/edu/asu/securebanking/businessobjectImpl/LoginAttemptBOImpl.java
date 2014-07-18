package edu.asu.securebanking.businessobjectImpl;

import edu.asu.securebanking.businessobject.CreateInternalAccountBO;
import edu.asu.securebanking.businessobject.LoginAttemptBO;
import edu.asu.securebanking.dataaccessobject.CreateInternalAccountDao;
import edu.asu.securebanking.dataaccessobject.LoginAttemptDao;
import edu.asu.securebanking.model.ExternalAccount;
import edu.asu.securebanking.model.InternalAccount;
import edu.asu.securebanking.model.LoginAttempt;


public class LoginAttemptBOImpl implements LoginAttemptBO {

	LoginAttemptDao LoginAttemptDao;
	 
	public void setLoginAttemptDao (LoginAttemptDao  LoginAttemptDao) {
		this.LoginAttemptDao = LoginAttemptDao;
	}

	@Override
	public void save(LoginAttempt LoginAttempt) {
		// TODO Auto-generated method stub
		LoginAttemptDao.save(LoginAttempt);
	}

	@Override
	public void update(LoginAttempt LoginAttempt) {
		// TODO Auto-generated method stub
		LoginAttemptDao.update(LoginAttempt);
	}

	@Override
	public void delete(LoginAttempt LoginAttempt) {
		// TODO Auto-generated method stub
		LoginAttemptDao.delete(LoginAttempt);
	}

	@Override
	public ExternalAccount findAccountByNameExternal(String username) {
		// TODO Auto-generated method stub
		ExternalAccount ea = LoginAttemptDao.findAccountByNameExternal(username);
		if(ea != null){
			return ea;
		}
		return null;
	}

	@Override
	public InternalAccount findAccountByNameInternal(String username) {
		// TODO Auto-generated method stub
		InternalAccount ia = LoginAttemptDao.findAccountByNameInternal(username);
		if(ia != null){
			return ia;
		}
		return null;
	}
	@Override
	public String findRecoveryAccount(String username, String email, String tel, String sec_question, String sec_answer) {
		// TODO Auto-generated method stub
		String result;
		result = LoginAttemptDao.findRecoveryAccount(username, email, tel, sec_question, sec_answer);

		return result;
	}
}
