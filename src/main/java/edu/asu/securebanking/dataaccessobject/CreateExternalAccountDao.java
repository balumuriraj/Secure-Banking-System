package edu.asu.securebanking.dataaccessobject;

import java.util.List;

import edu.asu.securebanking.hibernateexception.InternalException;

import edu.asu.securebanking.model.ExternalAccount;
import edu.asu.securebanking.model.InternalAccount;


public interface CreateExternalAccountDao {

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
