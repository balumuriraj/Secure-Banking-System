package edu.asu.securebanking.businessobjectImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.securebanking.businessobject.InternalCriticalTransactionBO;
import edu.asu.securebanking.dataaccessobject.CreateInternalAccountDao;
import edu.asu.securebanking.dataaccessobject.InternalCriticalTransactionDao;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.InternalCriticalTransaction;

public class InternalCriticalTransactionBOImpl implements InternalCriticalTransactionBO{

	InternalCriticalTransactionDao internalCriticalTransactionDao;
	
	public void setInternalCriticalTransactionDao (InternalCriticalTransactionDao  internalCriticalTransactionDao) {
		this.internalCriticalTransactionDao = internalCriticalTransactionDao;
	}
	
	@Override
	public List<InternalCriticalTransaction> getdetails() {
		// TODO Auto-generated method stub
		return internalCriticalTransactionDao.getdetails();
	}

	@Override
	public void save(InternalCriticalTransaction internalCriticalTransaction)  throws InternalException {
		// TODO Auto-generated method stub
		internalCriticalTransactionDao.save(internalCriticalTransaction);
	}

	@Override
	public InternalCriticalTransaction updatebyempidfromceo(int id, String msg)
			throws InternalException {
		// TODO Auto-generated method stub
		return internalCriticalTransactionDao.updatebyempidfromceo(id, msg);
	}

	@Override
	public InternalCriticalTransaction updatebyempidfrompresident(int id,
			String msg) throws InternalException {
		// TODO Auto-generated method stub
		return internalCriticalTransactionDao.updatebyempidfrompresident(id, msg);
	}

	@Override
	public InternalCriticalTransaction updatebyempidfromvpresident(int id,
			String msg) throws InternalException {
		// TODO Auto-generated method stub
		return internalCriticalTransactionDao.updatebyempidfromvpresident(id, msg);
	}


}
