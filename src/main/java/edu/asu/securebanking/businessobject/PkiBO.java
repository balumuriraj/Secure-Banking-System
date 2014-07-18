package edu.asu.securebanking.businessobject;

import java.security.KeyPair;

import com.mysql.jdbc.Blob;

import edu.asu.securebanking.model.PassKey;
import edu.asu.securebanking.model.Pki;

public interface PkiBO {
	void save(Pki pki);
	Pki findPKI(String username);
	Blob createKeyBlob(KeyPair keyPair);
	void update(Pki pki);
	void delete(Pki pki);
	
}
