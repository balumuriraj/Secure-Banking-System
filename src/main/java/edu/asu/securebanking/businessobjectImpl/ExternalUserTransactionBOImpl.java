package edu.asu.securebanking.businessobjectImpl;

import java.util.List;

import edu.asu.securebanking.businessobject.CreateExternalAccountBO;
import edu.asu.securebanking.businessobject.ExternalUserTransactionBO;
import edu.asu.securebanking.dataaccessobject.CreateExternalAccountDao;
import edu.asu.securebanking.dataaccessobject.ExternalUserTransactionDao;
import edu.asu.securebanking.model.ExternalAccount;
import edu.asu.securebanking.model.ExternalUserTransaction;
import edu.asu.securebanking.model.InternalUserTransaction;


public class ExternalUserTransactionBOImpl implements ExternalUserTransactionBO {

	ExternalUserTransactionDao externalUserTransactionDao;
	 
	public void setExternalUserTransactionDao (ExternalUserTransactionDao  ExternalUserTransactionDao) {
		this.externalUserTransactionDao = ExternalUserTransactionDao;
	}

	@Override
	public void save(ExternalUserTransaction ExternalUserTransaction) {
		// TODO Auto-generated method stub
		externalUserTransactionDao.save(ExternalUserTransaction);
	}

	@Override
	public void update(ExternalUserTransaction ExternalUserTransaction) {
		// TODO Auto-generated method stub
		externalUserTransactionDao.update(ExternalUserTransaction);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		externalUserTransactionDao.delete(id);
	}

	@Override
	public ExternalUserTransaction findTransactionById(int id) {
		// TODO Auto-generated method stub
		return externalUserTransactionDao.findTransactionById(id);
	}

	@Override
	public List getAllExternalUsersTransactions() {
		return externalUserTransactionDao.getAllExternalUsersTransactions();
	}
	
	@Override
	public void deleteByUserId(int userId) {
		this.externalUserTransactionDao.deleteByUserId(userId);
	}


}
