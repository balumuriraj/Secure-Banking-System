package edu.asu.securebanking.dataaccessobject;



import edu.asu.securebanking.model.PassKey;

public interface PassKeyDao {
	void save(PassKey passKey);
	void update(PassKey passKey);
	void delete(String passKey);
	PassKey findPassKey(String passKey);
}
