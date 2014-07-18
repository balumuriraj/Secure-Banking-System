package edu.asu.securebanking.businessobjectImpl;

import java.util.List;

import edu.asu.securebanking.businessobject.CreateInternalAccountBO;
import edu.asu.securebanking.businessobject.InternalUserTransactionBO;
import edu.asu.securebanking.dataaccessobject.CreateInternalAccountDao;
import edu.asu.securebanking.dataaccessobject.InternalUserTransactionDao;
import edu.asu.securebanking.model.InternalAccount;
import edu.asu.securebanking.model.InternalUserTransaction;


public class InternalUserTransactionBOImpl implements InternalUserTransactionBO {

	InternalUserTransactionDao internalUserTransactionDao;
	 
	public void setinternalUserTransactionDao (InternalUserTransactionDao  internalUserTransactionDao) {
		this.internalUserTransactionDao = internalUserTransactionDao;
	}

	@Override
	public String save(InternalUserTransaction internalUserTransaction) {
		// TODO Auto-generated method stub
		return internalUserTransactionDao.save(internalUserTransaction);
	}

	@Override
	public void update(InternalUserTransaction internalUserTransaction) {
		// TODO Auto-generated method stub
		internalUserTransactionDao.update(internalUserTransaction);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		internalUserTransactionDao.delete(id);
	}

	@Override
	public InternalUserTransaction findTransactionById(int id) {
		// TODO Auto-generated method stub
		return internalUserTransactionDao.findTransactionById(id);
	}

	public InternalUserTransaction findTransactionByIdAndDeptId(int id, int deptid) {
		// TODO Auto-generated method stub
		return internalUserTransactionDao.findUserByidAndDeptId(id, deptid);
	}
	
	@Override
	public List getAllInternalUserTransactions() {
		// TODO Auto-generated method stub
		return internalUserTransactionDao.getAllInternalTransactions();
	}
	
	@Override
	public List getDeptInternalUserTransactions(int deptid) {
		// TODO Auto-generated method stub
		return internalUserTransactionDao.getDeptInternalTransactions(deptid);
	}
	@Override
	public void deleteByEmpId(int empid) {
		// TODO Auto-generated method stub
		internalUserTransactionDao.deleteByEmpId(empid);
	}

}
