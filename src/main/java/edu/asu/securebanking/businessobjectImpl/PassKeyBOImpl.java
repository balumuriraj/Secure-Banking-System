package edu.asu.securebanking.businessobjectImpl;



import edu.asu.securebanking.businessobject.PassKeyBO;
import edu.asu.securebanking.dataaccessobject.PassKeyDao;
import edu.asu.securebanking.model.PassKey;

public class PassKeyBOImpl implements PassKeyBO {

	PassKeyDao passKeyDao;
	public void setPassKeyDao (PassKeyDao passKeyDao) {
		this.passKeyDao = passKeyDao;
	}
	public PassKeyDao getPassKeyDao()
	{
		return this.passKeyDao;
	}
	@Override
	public void save(PassKey passKey) {
		// TODO Auto-generated method stub
		passKeyDao.save(passKey);
	}
	@Override
	public void update(PassKey passKey) {
		// TODO Auto-generated method stub
		passKeyDao.update(passKey);
	}
	@Override
	public void delete(String passKey) {
		// TODO Auto-generated method stub
		passKeyDao.delete(passKey);
	}
	@Override
	public PassKey findPassKey(String passKey) {
		// TODO Auto-generated method stub
		System.out.println("checkA");
		return passKeyDao.findPassKey(passKey);
	}

}
