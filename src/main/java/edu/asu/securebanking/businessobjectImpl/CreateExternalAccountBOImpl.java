package edu.asu.securebanking.businessobjectImpl;

import java.util.List;

import edu.asu.securebanking.businessobject.CreateExternalAccountBO;
import edu.asu.securebanking.dataaccessobject.CreateExternalAccountDao;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.ExternalAccount;


public class CreateExternalAccountBOImpl implements CreateExternalAccountBO {

	CreateExternalAccountDao createExternalAccountDao;
	 
	public void setCreateExternalAccountDao (CreateExternalAccountDao  createExternalAccountDao) {
		this.createExternalAccountDao = createExternalAccountDao;
	}

	@Override
	public String save(ExternalAccount externalAccount) {
		// TODO Auto-generated method stub
		return createExternalAccountDao.save(externalAccount);
	}

	@Override
	public void update(ExternalAccount externalAccount) {
		// TODO Auto-generated method stub
		createExternalAccountDao.update(externalAccount);
	}

	@Override
	public void delete(ExternalAccount externalAccount) {
		// TODO Auto-generated method stub
		createExternalAccountDao.delete(externalAccount);
	}

	@Override
	public ExternalAccount findAccountByName(String externalAccountUserName) {
		// TODO Auto-generated method stub
		return createExternalAccountDao.findAccountByName(externalAccountUserName);
	}

	@Override

	public List<ExternalAccount> getAllExternalUserAccounts() {
		// TODO Auto-generated method stub
		return createExternalAccountDao.getAllExternalUserAccounts();
	}

	public List<ExternalAccount> getdetails() {
		// TODO Auto-generated method stub
		return createExternalAccountDao.getdetails();
	}

	@Override
	public ExternalAccount findUserByid(int id) throws InternalException {
		// TODO Auto-generated method stub
		return createExternalAccountDao.findUserByid(id);
	}

	@Override
	public List<ExternalAccount> getpiireq() {
		// TODO Auto-generated method stub
		return createExternalAccountDao.getpiireq();

	}

	@Override
	public List<ExternalAccount> getccreq() {
		// TODO Auto-generated method stub
		return createExternalAccountDao.getccreq();
	}

}
