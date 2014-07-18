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

import edu.asu.securebanking.dataaccessobject.InternalUserTransactionDao;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.ExternalAccount;
import edu.asu.securebanking.model.InternalAccount;
import edu.asu.securebanking.model.InternalUserTransaction;

@Repository
@SuppressWarnings({"unchecked", "rawtypes"})
@Transactional
public class InternalUserTransactionDaoImpl  implements InternalUserTransactionDao {
	
    //get log4j handler
private static final Logger logger = Logger.getLogger(InternalUserTransactionDaoImpl.class);

	@Autowired private SessionFactory sessionFactory;
	
		

	    public  SessionFactory getSessionFactory() {
	        return sessionFactory;
	    }

	    public  void setSessionFactory(SessionFactory sessionFactory) {
	        this.sessionFactory = sessionFactory;
	    }
@Override
	public String save(InternalUserTransaction internalUserTransaction) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findinternaluser");
		query.setParameter("employeeId", internalUserTransaction.getEmployeeId());
		System.out.println("size of list is: "+query.list().size());
		if (query.list().size() == 0){
			return null;
		}else{
			session.save(internalUserTransaction);
			logger.info("Internal User Transaction  Added -- Employee Id" + internalUserTransaction.getEmployeeId()+" -- TransactionId --" + internalUserTransaction.getTransId());
			return "success";
		}
	}

		@Override
		public void update(InternalUserTransaction internalUserTransaction) {
			// TODO Auto-generated method stub
			Session session = sessionFactory.getCurrentSession();
			internalUserTransaction.setStatus("approved");
			session.update(internalUserTransaction);
			 logger.info("Internal User Transaction  Updated -- Employee Id" + internalUserTransaction.getEmployeeId()+" -- TransactionId --" + internalUserTransaction.getTransId()); 
		}

		@Override
		public void delete(int id) {
			// TODO Auto-generated method stub
		    Session session = sessionFactory.getCurrentSession();
		    InternalUserTransaction transaction = (InternalUserTransaction) session.get(InternalUserTransaction.class, id);
			if (transaction != null)
				session.delete(transaction);
			 logger.info("Internal User Transaction  Deleted -- Employee Id" + transaction.getEmployeeId()+" -- TransactionId --" + transaction.getTransId()); 
		}

		@Override
		public InternalUserTransaction findUserByid(int id) {
			
			Session session = sessionFactory.getCurrentSession();
		
		    Query query = session.getNamedQuery("findinternaltransaction");
			query.setParameter("transId", id);
			System.out.println("size of list is: "+query.list().size());
			if (query.list().size() == 0)
				throw new InternalException("Transaction ID does not exist in the database!");
			List<InternalUserTransaction> list = query.list();
			
			InternalUserTransaction queryResult = list.get(0);
			
			 logger.info("Internal User Transaction  Searched -- Employee Id" + queryResult.getEmployeeId()+" -- TransactionId --" + queryResult.getTransId()); 
			
            return queryResult;
			
            
		}

		@Override
		public InternalUserTransaction findUserByidAndDeptId(int id, int deptid) {
			
			Session session = sessionFactory.getCurrentSession();
		
			List<InternalUserTransaction> list = (List<InternalUserTransaction>)session.createCriteria(InternalUserTransaction.class) 
					.add( Restrictions.eq("transId", id ) ) 
					.add( Restrictions.eq("deptId", deptid ) )
					.list();
			
			if (list.size() == 0)
				throw new InternalException("Transaction ID does not exist in the database!");
			
			InternalUserTransaction queryResult = list.get(0);
			
			System.out.println("transaction.getEmployeeId()"+ list.get(0).getEmployeeId() + "transaction.getDescription()" + list.get(0).getDescription()+"transaction.getTransId()" + list.get(0).getTransId()+" transaction.getStatus()" +list.get(0).getStatus());

			 logger.info("Internal User Transaction  Searched -- Dept Id " + deptid +" -- TransactionId --" + id); 
			
            return queryResult;
			
			//return allInternalTransactions.get(0);
			// return (InternalUserTransaction) allInternalTransactions.get(0);
		}
		@Override
		public List getAllInternalTransactions() {
		   
			    Session session = sessionFactory.getCurrentSession();
			    
			    List<InternalUserTransaction> allInternalTransactions = session.createQuery("from edu.asu.securebanking.model.InternalUserTransaction").list();
			       
			  for(InternalUserTransaction intUsr : allInternalTransactions)
			  {
				  System.out.println(" intUsr.getTransId()====>" + intUsr.getTransId());
				  System.out.println(" intUsr.getEmployeeId()====>" + intUsr.getEmployeeId());
				  System.out.println(" intUsr.getTransType()====>" + intUsr.getTransType());
				  System.out.println(" intUsr.getDescription()====>" + intUsr.getDescription());
				  System.out.println( " intUsr.getStatus()====>"  +intUsr.getStatus());
			  }
			  
			  logger.info("Searched All internal User Transactions -- number of enteries " + allInternalTransactions.size()); 
			    
			    return allInternalTransactions;
			}
		@Override
		public List getDeptInternalTransactions(int deptid) {
		   
			    Session session = sessionFactory.getCurrentSession();
			    
			    //List<InternalUserTransaction> allInternalTransactions = session.createQuery("from edu.asu.securebanking.model.InternalUserTransaction").list();
			       
				List<InternalUserTransaction> allInternalTransactions = (List<InternalUserTransaction>)session.createCriteria(InternalUserTransaction.class) 
						.add( Restrictions.eq("deptId", deptid ) ) 
						.list();
			 
				  logger.info("Searched All internal User Transactions for Department with Dept Id " + deptid); 
			  
			    return allInternalTransactions;
			}
		@Override
		public void deleteByEmpId(int empid){
			Session session = sessionFactory.getCurrentSession();

			List<InternalUserTransaction> results = (List<InternalUserTransaction>)session.createCriteria(InternalUserTransaction.class) 
					.add( Restrictions.eq("employeeId", empid ) ) 
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

		@Override
		public InternalUserTransaction findTransactionById(int id) {
			
			Session session = sessionFactory.getCurrentSession();
			
		    Query query = session.getNamedQuery("findinternaltransaction");
			query.setParameter("transId", id);
			System.out.println("size of list is: "+query.list().size());
			if (query.list().size() == 0)
				throw new InternalException("Transaction ID does not exist in the database!");
			List<InternalUserTransaction> list = query.list();
			
			InternalUserTransaction queryResult = list.get(0);
			
			 logger.info("Internal User Transaction  Searched -- Employee Id" + queryResult.getEmployeeId()+" -- TransactionId --" + queryResult.getTransId()); 
			
            return queryResult;
		}
}
