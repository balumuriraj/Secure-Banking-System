package edu.asu.securebanking.businessobjectImpl;

import java.security.KeyPair;

import com.mysql.jdbc.Blob;

import edu.asu.securebanking.businessobject.PkiBO;
import edu.asu.securebanking.dataaccessobject.PkiDao;
import edu.asu.securebanking.dataaccessobject.PiiAuthDao;
import edu.asu.securebanking.model.Pki;

public class PkiBOImpl implements PkiBO{

	PkiDao PkiDao;
	
	public void setPkiDao (PkiDao  PkiDao) {
		this.PkiDao = PkiDao;
	}
	
	public PkiDao getPKIDao () {
		return PkiDao;
	}
	
	@Override
	public void save(Pki pki) {
		// TODO Auto-generated method stub
		PkiDao.save(pki);
	}

	@Override
	public Pki findPKI(String username) {
		// TODO Auto-generated method stub
		return PkiDao.findPKI(username);
	}

	@Override
	public Blob createKeyBlob(KeyPair keyPair) {
		// TODO Auto-generated method stub
		return PkiDao.createKeyBlob(keyPair);
	}

	@Override
	public void update(Pki pki) {
		// TODO Auto-generated method stub
		PkiDao.update(pki);
	}

	@Override
	public void delete(Pki pki) {
		// TODO Auto-generated method stub
	     PkiDao.delete(pki);
	}

}
