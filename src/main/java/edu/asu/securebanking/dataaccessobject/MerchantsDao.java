package edu.asu.securebanking.dataaccessobject;

import java.util.List;

import edu.asu.securebanking.model.ExternalAccount;

public interface MerchantsDao {

    List getAllMerchantsTransactions(int userId);
	
	ExternalAccount getAccountByAccountNumber(String accountNo);
}
