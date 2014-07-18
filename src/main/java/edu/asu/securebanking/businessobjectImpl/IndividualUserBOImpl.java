package edu.asu.securebanking.businessobjectImpl;

import java.util.List;

import edu.asu.securebanking.businessobject.IndividualUserBO;
import edu.asu.securebanking.dataaccessobject.IndividualUserDao;
import edu.asu.securebanking.model.ExternalAccount;



public class IndividualUserBOImpl implements IndividualUserBO{


	private IndividualUserDao individualUserDao;
	
	public void setIndividualUserDao (IndividualUserDao  individualUserDao) {
		this.individualUserDao = individualUserDao;
	}
	
	@Override
	public List getAllUserTransactions(int userId) {
		return individualUserDao.getAllExternalUsersTransactions(userId);
		
	}
	
	@Override
	public ExternalAccount getAccountByAccountNumber(String accountNo){
		
		return individualUserDao.getAccountByAccountNumber(accountNo);
	}

	@Override
	public ExternalAccount getAccountByUsername(String username) {
		// TODO Auto-generated method stub
		return individualUserDao.getAccountByUsername(username);
	}
	

}
