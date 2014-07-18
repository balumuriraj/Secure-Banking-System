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

import edu.asu.securebanking.dataaccessobject.ExternalUserTransactionDao;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.ExternalUserTransaction;
import edu.asu.securebanking.model.InternalUserTransaction;

@Repository
@SuppressWarnings({"unchecked", "rawtypes"})
@Transactional
public class ExternalUserTransactionDaoImpl  implements ExternalUserTransactionDao {

	@Autowired private SessionFactory sessionFactory;
	
	private static final Logger logger = Logger.getLogger(ExternalUserTransactionDaoImpl.class);
		

	    public  SessionFactory getSessionFactory() {
	        return sessionFactory;
	    }

	    public  void setSessionFactory(SessionFactory sessionFactory) {
	        this.sessionFactory = sessionFactory;
	    }

		@Override
		public void save(ExternalUserTransaction externalUserTransaction) {
			// TODO Auto-generated method stub
			Session session = sessionFactory.getCurrentSession();
			session.save(externalUserTransaction);
			
			logger.info("External User Transaction -- Created-- Transaction Id  " + externalUserTransaction.getTransId()); 
		}

		@Override
		public void update(ExternalUserTransaction externalUserTransaction) {
			// TODO Auto-generated method stub
			Session session = sessionFactory.getCurrentSession();
			session.update(externalUserTransaction);
			logger.info("External User Transaction -- Updated-- Transaction Id  " + externalUserTransaction.getTransId()); 
		}

		@Override
		public void delete(int id) {
			// TODO Auto-generated method stub
			  Session session = sessionFactory.getCurrentSession();
			    ExternalUserTransaction transaction = (ExternalUserTransaction) session.get(ExternalUserTransaction.class, id);
				if (transaction != null)
					session.delete(transaction);
				logger.info("External User Transaction -- Deleted-- Transaction Id  " + id); 
		}

		@Override
		public ExternalUserTransaction findTransactionById(int id) {
			
			Session session = sessionFactory.getCurrentSession();
			
		    Query query = session.getNamedQuery("findexternaltransaction");
			query.setParameter("transId", id);
			System.out.println("size of list is: "+query.list().size());
			if (query.list().size() == 0)
				throw new InternalException("Transaction ID does not exist in the database!");
			List<ExternalUserTransaction> list = query.list();
			
			ExternalUserTransaction queryResult = list.get(0);
			logger.info("External User Transaction -- Searched-- Transaction Id  " + id); 
            return queryResult;
		}

		@Override
		public List getAllExternalUsersTransactions() {
			
			 Session session = sessionFactory.getCurrentSession();
			    
			    List<ExternalUserTransaction> allExternalTransactions = session.createQuery("from edu.asu.securebanking.model.ExternalUserTransaction").list();
			       
			  for(ExternalUserTransaction extUsr : allExternalTransactions)
			  {
				  System.out.println(" extUsr.getTransId()====>" + extUsr.getTransId());
				  System.out.println(" extUsr.getUserId()====>" + extUsr.getUserId());
				  System.out.println(" intUsr.getTransType()====>" + extUsr.getTransType());
				  System.out.println(" extUsr.getTransDetail()====>" + extUsr.getTransDetail());
				  System.out.println( " extUsr.getAmountInvolved()" + extUsr.getAmountInvolved());
				  System.out.println( " extUsr.getStatus()====>"  +extUsr.getStatus());
			  }
				logger.info("All External User Transaction -- Searched-- No of records " + allExternalTransactions.size());
			    return allExternalTransactions;
		}

		@Override
		public void deleteByUserId(int userId){
			Session session = sessionFactory.getCurrentSession();

			List<ExternalUserTransaction> results = (List<ExternalUserTransaction>)session.createCriteria(ExternalUserTransaction.class) 
					.add( Restrictions.eq("userId", userId ) ) 
					.list();
			if(results.size() == 0){
				System.out.println("we don't have any matching internalusertransaction object in the DB!!");
			}else{
				for(int i=0;i<results.size();i++){
					this.delete(results.get(i).getTransId());
					System.out.println("InternalUserTransaction: "+Integer.toString(i)+" th object has been deleted from the DB!!");
				}
			}
		}
}
