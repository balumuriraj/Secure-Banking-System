package edu.asu.securebanking.dataaccessobject;

import java.util.List;

import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.InternalCriticalTransaction;

public interface InternalCriticalTransactionDao {

	List<InternalCriticalTransaction> getdetails(); 
	void save(InternalCriticalTransaction internalCriticalTransaction) throws InternalException;
	InternalCriticalTransaction updatebyempidfromceo(int id, String msg) throws InternalException;
	InternalCriticalTransaction updatebyempidfrompresident(int id, String msg) throws InternalException;
	InternalCriticalTransaction updatebyempidfromvpresident(int id, String msg) throws InternalException;

}
