package edu.asu.securebanking.businessobject;

import java.util.List;

import edu.asu.securebanking.model.ExternalAccount;

public interface MerchantsBO {

	List getAllMerchantsTransactions(int userId);
	
	ExternalAccount getAccountByAccountNumber(String accountNo);
}
