 package edu.asu.securebanking.dataaccessobjectImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.securebanking.dataaccessobject.InternalCriticalTransactionDao;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.InternalCriticalTransaction;

@Repository
@Transactional
public class InternalCriticalTransactionDaoImpl implements InternalCriticalTransactionDao{
	
	@Autowired private SessionFactory sessionFactory;
	

private static final Logger logger = Logger.getLogger(InternalCriticalTransactionDaoImpl.class);

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<InternalCriticalTransaction> getdetails() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("getallcriticaltrans");
		List list = query.list();
		logger.info("All Internal Critical Transactions -- Searched -- No of records  " + list.size()); 
		return list;
	}

	@Override
	public void save(InternalCriticalTransaction internalCriticalTransaction)  throws InternalException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.getNamedQuery("findcriticaluser");
		query.setParameter("employeeId", internalCriticalTransaction.getEmployeeId());
		System.out.println("size of list is: "+query.list().size());
		if (!(query.list().size() == 0))
			throw new InternalException("User already in Critical Transaction Queue!");
		else{
			session.save(internalCriticalTransaction);
		}
		logger.info("Internal Critical Transactions -- Created -- Transaction Id " + internalCriticalTransaction.getTransId());
	}

	@Override
	public InternalCriticalTransaction updatebyempidfromceo(int id, String msg)
			throws InternalException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query1 = session.getNamedQuery("updateinternalcriticaltransactionbyceo");
		query1.setParameter("employeeId", id);
		query1.setParameter("ceoapp", msg);
		int result = query1.executeUpdate();
		System.out.println("Rows affected: "+ result);
		
		Query query = session.getNamedQuery("findcriticaluser");
		query.setParameter("employeeId", id);
		System.out.println("size of list is: "+query.list().size());
		if (query.list().size() == 0)
			throw new InternalException("User does not exist in the database!");
		List<?> list = query.list();
		logger.info("Internal Critical Transactions -- Updated by CEO -- Employee Id --"+ id);
		return (InternalCriticalTransaction)list.get(0);

	}

	@Override
	public InternalCriticalTransaction updatebyempidfrompresident(int id,
			String msg) throws InternalException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query1 = session.getNamedQuery("updateinternalcriticaltransactionbypres");
		query1.setParameter("employeeId", id);
		query1.setParameter("presapp", msg);
		int result = query1.executeUpdate();
		System.out.println("Rows affected: "+ result);
		
		Query query = session.getNamedQuery("findcriticaluser");
		query.setParameter("employeeId", id);
		System.out.println("size of list is: "+query.list().size());
		if (query.list().size() == 0)
			throw new InternalException("User does not exist in the database!");
		List<?> list = query.list();
		logger.info("Internal Critical Transactions -- Updated by PRESIDENT -- Employee Id --"+ id);
		return (InternalCriticalTransaction)list.get(0);
	}

	@Override
	public InternalCriticalTransaction updatebyempidfromvpresident(int id,
			String msg) throws InternalException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query1 = session.getNamedQuery("updateinternalcriticaltransactionbyvpres");
		query1.setParameter("employeeId", id);
		query1.setParameter("vpresapp", msg);
		int result = query1.executeUpdate();
		System.out.println("Rows affected: "+ result);
		
		Query query = session.getNamedQuery("findcriticaluser");
		query.setParameter("employeeId", id);
		System.out.println("size of list is: "+query.list().size());
		if (query.list().size() == 0)
			throw new InternalException("User does not exist in the database!");
		List<?> list = query.list();
		logger.info("Internal Critical Transactions -- Updated by VICE PRESIDENT -- Employee Id --"+ id);
		return (InternalCriticalTransaction)list.get(0);
	}

}
