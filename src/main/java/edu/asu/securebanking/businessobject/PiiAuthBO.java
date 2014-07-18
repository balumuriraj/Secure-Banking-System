package edu.asu.securebanking.businessobject;

import java.util.List;

import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.PiiAuth;

public interface PiiAuthBO {
	
	List<PiiAuth> getallPiiAuth();
	void save(PiiAuth piiAuth) throws InternalException;
	void authorize(int id, String msg) throws InternalException;

}
