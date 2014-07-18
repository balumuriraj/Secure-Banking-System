package edu.asu.securebanking.businessobject;

import java.util.List;


import edu.asu.securebanking.hibernateexception.InternalException;

import edu.asu.securebanking.model.ExternalAccount;

public interface CreateExternalAccountBO {

	String save(ExternalAccount externalAccount);
	void update(ExternalAccount externalAccount);
	void delete(ExternalAccount externalAccount);
	ExternalAccount findAccountByName(String userName);

	List<ExternalAccount> getAllExternalUserAccounts();

	List<ExternalAccount> getdetails();
	ExternalAccount findUserByid(int id) throws InternalException;
	List<ExternalAccount> getpiireq();
	List<ExternalAccount> getccreq();

}
