package edu.asu.securebanking.businessobject;

import java.util.List;

import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.CreditcardRequests;

public interface CreditcardRequestsBO {
	List<CreditcardRequests> getallCreditcardRequests();
	void save(CreditcardRequests ccrequests) throws InternalException;
	void approved(String accountNo, String msg) throws InternalException;
	void rejected(String accountNo) throws InternalException;
	CreditcardRequests checkForCreditCardNumber(String accountNumber);

}
