package edu.asu.securebanking.businessobjectImpl;

import java.util.List;

import edu.asu.securebanking.businessobject.MerchantsBO;
import edu.asu.securebanking.dataaccessobject.MerchantsDao;
import edu.asu.securebanking.model.ExternalAccount;

public class MerchantsBOImpl implements MerchantsBO {

	private MerchantsDao merchantsDao;
	
	public void setMerchantsDao (MerchantsDao  merchantsDao) {
		this.merchantsDao = merchantsDao;
	}
	
	@Override
	public List getAllMerchantsTransactions(int userId) {
		// TODO Auto-generated method stub
		return merchantsDao.getAllMerchantsTransactions(userId);
	}

	@Override
	public ExternalAccount getAccountByAccountNumber(String accountNo) {
		// TODO Auto-generated method stub
		return merchantsDao.getAccountByAccountNumber(accountNo);
	}


	

}
