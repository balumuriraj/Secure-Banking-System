package edu.asu.securebanking.dataaccessobject;

import java.util.List;

import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.InternalAccount;

public interface CreateInternalAccountDao {
	
	String save(InternalAccount internalAccount);
	void update(InternalAccount internalAccount);
	void delete(int id);
	void deleteUser(InternalAccount internalAccount);
	void transferinternaluser(int id, int deptid) throws InternalException;
	InternalAccount findUserByid(int id) throws InternalException;

	List<InternalAccount> getAllInternalUserAccounts(); 

	InternalAccount findUserByidanddeptid(int id, int deptid) throws InternalException;
	InternalAccount findUserByusername(String username) throws InternalException;
	List<InternalAccount> getdetails(); 
	
	void updatesalary(int id, double salary) throws InternalException;

	
}
