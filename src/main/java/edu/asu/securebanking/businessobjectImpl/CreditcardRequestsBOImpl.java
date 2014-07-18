package edu.asu.securebanking.businessobjectImpl;

import java.util.List;

import edu.asu.securebanking.businessobject.CreditcardRequestsBO;
import edu.asu.securebanking.dataaccessobject.CreditcardRequestsDao;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.CreditcardRequests;

public class CreditcardRequestsBOImpl implements CreditcardRequestsBO{
	
	CreditcardRequestsDao ccRequestsDao;
	

	public void setCcRequestsDao(CreditcardRequestsDao ccRequestsDao) {
		this.ccRequestsDao = ccRequestsDao;
	}

	@Override
	public List<CreditcardRequests> getallCreditcardRequests() {
		return ccRequestsDao.getallCreditcardRequests();
	}

	@Override
	public void save(CreditcardRequests ccrequests) throws InternalException {
		// TODO Auto-generated method stub
		ccRequestsDao.save(ccrequests);
	}

	@Override
	public void approved(String accountNo, String msg) throws InternalException {
		// TODO Auto-generated method stub
		ccRequestsDao.approved(accountNo, msg);
	}

	@Override
	public void rejected(String accountNo) throws InternalException {
		// TODO Auto-generated method stub
		ccRequestsDao.rejected(accountNo);
	}

	@Override
	public CreditcardRequests checkForCreditCardNumber(String accountNumber) {
		// TODO Auto-generated method stub
		return ccRequestsDao.checkForCreditCardNumber(accountNumber);
	}
	
	

}
