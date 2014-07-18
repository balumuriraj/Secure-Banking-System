package edu.asu.securebanking.dataaccessobjectImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.securebanking.dataaccessobject.IndividualUserDao;
import edu.asu.securebanking.model.ExternalAccount;
import edu.asu.securebanking.model.ExternalUserTransaction;

@Repository
@Transactional
public class IndividualUserDaoImpl implements IndividualUserDao{

	@Autowired private SessionFactory sessionFactory;
	
	private static final Logger logger = Logger.getLogger(IndividualUserDaoImpl.class);
	
	public  SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public  void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public List<ExternalUserTransaction> getAllExternalUsersTransactions(int userId) {
		
		 Session session = sessionFactory.getCurrentSession();
		           
		    List<ExternalUserTransaction> allIndividualTransactions = (List<ExternalUserTransaction>)session.createCriteria(ExternalUserTransaction.class) 
					.add( Restrictions.eq("userId", userId)) 
					.list();
		    
			logger.info("All External Transactions -- Searched -- No of records  " + allIndividualTransactions.size()); 
		    
		    return allIndividualTransactions;
	}

	@Override
	public ExternalAccount getAccountByAccountNumber(String accountNo) {
		// TODO Auto-generated method stub
				Session session = sessionFactory.getCurrentSession();
				Query query = session.getNamedQuery("findexternaluseraccountnumber");
				query.setParameter("accountNo",accountNo);
				System.out.println("size of list is: "+query.list().size());
				if (query.list().size() == 0)
					return null;
				List<?> list = query.list();
				logger.info("External Account -- Searched -- Account Number  " + accountNo); 
				
				return (ExternalAccount)list.get(0);
	}

	@Override
	public ExternalAccount getAccountByUsername(String username) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findexternaluserbyusername");
		query.setParameter("username",username);
		System.out.println("size of list is: "+query.list().size());
		if (query.list().size() == 0)
			return null;
		List<?> list = query.list();
		logger.info("External Account -- Searched -- username " + username); 
		
		return (ExternalAccount)list.get(0);
	}
}
