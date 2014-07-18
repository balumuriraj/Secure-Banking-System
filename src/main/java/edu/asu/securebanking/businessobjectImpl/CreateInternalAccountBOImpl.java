package edu.asu.securebanking.businessobjectImpl;

import java.util.List;

import edu.asu.securebanking.businessobject.CreateInternalAccountBO;
import edu.asu.securebanking.dataaccessobject.CreateInternalAccountDao;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.InternalAccount;


public class CreateInternalAccountBOImpl implements CreateInternalAccountBO {

	CreateInternalAccountDao createInternalAccountDao;

	public void setCreateInternalAccountDao (CreateInternalAccountDao  createInternalAccountDao) {
		this.createInternalAccountDao = createInternalAccountDao;
	}

	@Override
	public String save(InternalAccount InternalAccount) {
		// TODO Auto-generated method stub
		return createInternalAccountDao.save(InternalAccount);
	}

	@Override
	public void update(InternalAccount InternalAccount) {
		// TODO Auto-generated method stub
		createInternalAccountDao.update(InternalAccount);
	}


	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		createInternalAccountDao.delete(id);
	}

	
	@Override
	public void deleteUser(InternalAccount internalAccount){
		// TODO Auto-generated method stub
		createInternalAccountDao.deleteUser(internalAccount);
	}
	@Override
	public InternalAccount findUserByid(int id) throws InternalException {
		// TODO Auto-generated method stub
		return createInternalAccountDao.findUserByid(id);
	}

	@Override
	public List<InternalAccount> getAllInternalUserAccounts() {
		// TODO Auto-generated method stub

		return createInternalAccountDao.getAllInternalUserAccounts();
			// TODO Auto-generated method stub;

		
	}

	@Override
	public void transferinternaluser(int id, int deptid) throws InternalException{
		// TODO Auto-generated method stub
		createInternalAccountDao.transferinternaluser(id, deptid);
	}

	@Override
	public InternalAccount findUserByidanddeptid(int id, int deptid)
			throws InternalException {
		// TODO Auto-generated method stub
		return createInternalAccountDao.findUserByidanddeptid(id, deptid);
	}

	@Override
	public InternalAccount findUserByusername(String username)
			throws InternalException {
		// TODO Auto-generated method stub
		return createInternalAccountDao.findUserByusername(username);

	}

	@Override
	public List<InternalAccount> getdetails() {
		// TODO Auto-generated method stub
		return createInternalAccountDao.getdetails();
	}

	@Override
	public void updatesalary(int id, double salary) throws InternalException {
		// TODO Auto-generated method stub
		createInternalAccountDao.updatesalary(id, salary);
	}

}
