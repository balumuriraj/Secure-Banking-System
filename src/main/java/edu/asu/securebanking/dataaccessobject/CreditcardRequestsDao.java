package edu.asu.securebanking.dataaccessobject;

import java.util.List;

import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.CreditcardRequests;


public interface CreditcardRequestsDao {
	List<CreditcardRequests> getallCreditcardRequests();
	void save(CreditcardRequests ccrequests) throws InternalException;
	void approved(String accountNo, String msg) throws InternalException;
	void rejected(String accountNo) throws InternalException;
	CreditcardRequests checkForCreditCardNumber(String accountNumber);
}
