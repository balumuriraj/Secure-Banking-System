package edu.asu.securebanking.dataaccessobject;

import java.util.List;

import edu.asu.securebanking.model.ExternalAccount;

public interface IndividualUserDao {

	List getAllExternalUsersTransactions(int userId);
	
	ExternalAccount getAccountByAccountNumber(String accountNo);
	ExternalAccount getAccountByUsername(String username);
}
