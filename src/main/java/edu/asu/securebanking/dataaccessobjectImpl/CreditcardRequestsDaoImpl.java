package edu.asu.securebanking.dataaccessobjectImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.securebanking.dataaccessobject.CreditcardRequestsDao;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.CreditcardRequests;

@Repository
@SuppressWarnings({"unchecked", "rawtypes"})
@Transactional
public class CreditcardRequestsDaoImpl implements CreditcardRequestsDao {

	private static final Logger logger = Logger.getLogger(CreditcardRequestsDaoImpl.class);
	
	@Autowired private SessionFactory sessionFactory;

	public  SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public  void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Override
	public List<CreditcardRequests> getallCreditcardRequests() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("getallapprovedccrequests");

		List list = query.list();
		logger.info("All Credit Cards Requests -- Searched-- No of Records " + list.size()); 
		return list;
	}

	@Override
	public void save(CreditcardRequests ccrequests) throws InternalException {
		Session session = sessionFactory.getCurrentSession();
		session.save(ccrequests);
		logger.info("Credit Cards Request -- Created -- Request Number " + ccrequests.getRequestNo()); 
	}

	@Override
	public void approved(String accountNo, String msg) throws InternalException {
		Session session = sessionFactory.getCurrentSession();
		Query query1 = session.getNamedQuery("approveccrequest");
		query1.setParameter("accountNo", accountNo);
		query1.setParameter("approved", msg);
		int result = query1.executeUpdate();
		System.out.println("Rows affected: "+ result);
		logger.info("Credit Cards Request -- Approved -- Account No " + accountNo); 
	}
	
	

	@Override
	public void rejected(String accountNo) throws InternalException {
		
		try {
			Session session = sessionFactory.getCurrentSession();
			System.out.println("accountNo: "+ accountNo);
		
			
			Query q = session.createQuery("from CreditcardRequests where accountNo = :accountNo ");
			q.setParameter("accountNo", accountNo);
			CreditcardRequests ccreq = (CreditcardRequests)q.list().get(0);
			session.delete(ccreq);
			logger.info("Credit Cards Request -- Rejected -- Account No " + accountNo); 
		    System.out.println("delete successful");
		  } catch (RuntimeException re) {
			  System.out.println("delete failed");
		    throw re;
		  }

	}

	@Override
	public CreditcardRequests checkForCreditCardNumber(String accountNumber) {
		
		Session session = sessionFactory.getCurrentSession();
		System.out.println("accountNo: "+ accountNumber);
	
		
		Query q = session.createQuery("from CreditcardRequests where accountNo = :accountNo ");
		q.setParameter("accountNo", accountNumber);
		logger.info("Credit Cards Request -- Checked -- Account No " + accountNumber); 
		if(q.list().size()>0)
		{
		CreditcardRequests ccreq = (CreditcardRequests)q.list().get(0);
		return ccreq;
		}
		else
			return null;
		
	}
	
	

}
