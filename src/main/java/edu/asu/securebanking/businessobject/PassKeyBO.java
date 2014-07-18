package edu.asu.securebanking.businessobject;



import edu.asu.securebanking.model.PassKey;

public interface PassKeyBO {
	void save(PassKey passKey);
	void update(PassKey passKey);
	void delete(String passKey);
	PassKey findPassKey(String passKey);
}
