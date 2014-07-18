package edu.asu.securebanking.businessobjectImpl;

import java.util.List;

import edu.asu.securebanking.businessobject.PiiAuthBO;
import edu.asu.securebanking.dataaccessobject.PiiAuthDao;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.PiiAuth;

public class PiiAuthBOImpl implements PiiAuthBO{
	
	PiiAuthDao piiAuthDao;
	 
	public void setPiiAuthDao (PiiAuthDao  piiAuthDao) {
		this.piiAuthDao = piiAuthDao;
	}


	@Override
	public void save(PiiAuth piiAuth) throws InternalException {
		// TODO Auto-generated method stub
		
		System.out.println("Entered BO!");
		piiAuthDao.save(piiAuth);
	}


	@Override
	public List<PiiAuth> getallPiiAuth() {
		// TODO Auto-generated method stub
		return piiAuthDao.getallPiiAuth();
	}


	@Override
	public void authorize(int id, String msg) throws InternalException {
		// TODO Auto-generated method stub
		piiAuthDao.authorize(id, msg);
	}

}
