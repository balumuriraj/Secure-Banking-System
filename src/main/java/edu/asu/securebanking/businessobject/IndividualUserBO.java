package edu.asu.securebanking.businessobject;

import java.util.List;

import edu.asu.securebanking.model.ExternalAccount;

public interface IndividualUserBO {

	List getAllUserTransactions(int userId);
	
	ExternalAccount getAccountByAccountNumber(String accountNo);
	ExternalAccount getAccountByUsername(String username);
}
