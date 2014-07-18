package edu.asu.securebanking.dataaccessobjectImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.securebanking.dataaccessobject.LoginAttemptDao;
import edu.asu.securebanking.model.ExternalAccount;
import edu.asu.securebanking.model.InternalAccount;
import edu.asu.securebanking.model.LoginAttempt;

@Repository
@SuppressWarnings({"unchecked", "rawtypes"})
@Transactional
public class LoginAttemptDaoImpl  implements LoginAttemptDao {

	@Autowired private SessionFactory sessionFactory;
	

  private static final Logger logger = Logger.getLogger(LoginAttemptDaoImpl.class);
		

	    public  SessionFactory getSessionFactory() {
	        return sessionFactory;
	    }

	    public  void setSessionFactory(SessionFactory sessionFactory) {
	        this.sessionFactory = sessionFactory;
	    }

		@Override
		public void save(LoginAttempt LoginAttempt) {
			// TODO Auto-generated method stub
			Session session = sessionFactory.getCurrentSession();
			session.save(LoginAttempt);
			
			logger.info("Login Attempt User -- Created-- Username " + LoginAttempt.getuser_name()); 
		}

		@Override
		public void update(LoginAttempt LoginAttempt) {
			// TODO Auto-generated method stub
			Session session = sessionFactory.getCurrentSession();
			session.update(LoginAttempt);
			logger.info("Login Attempt User -- Updated-- Username " + LoginAttempt.getuser_name()); 
		}

		@Override
		public void delete(LoginAttempt LoginAttempt) {
			// TODO Auto-generated method stub
			Session session = sessionFactory.getCurrentSession();
			session.delete(LoginAttempt);
			logger.info("Login Attempt User -- Updated-- Username " + LoginAttempt.getuser_name()); 
		}

		@Override
		public ExternalAccount findAccountByNameExternal(String username) {
			// TODO Auto-generated method stub
			System.out.println("This is LoginAttemptDaoImpl-ExternalAccount area");
			Session session = sessionFactory.getCurrentSession();

			List<ExternalAccount> results = (List<ExternalAccount>)session.createCriteria(ExternalAccount.class) 
					.add( Restrictions.eq("username", username ) ) 
					.list();
			logger.info("Login Attempt User -- Searched-- Username " + username); 
			if(results.size() == 0){
				return null;
			}else if(results.size() >= 1){
				System.out.println(results.get(0).toString());
				System.out.println("address = "+results.get(0).getAddress());
				return results.get(0);
			}
			return null;
		}
		@Override
		public InternalAccount findAccountByNameInternal(String username) {
			// TODO Auto-generated method stub
			System.out.println("This is LoginAttemptDaoImpl-InternalAccount area");
			Session session = sessionFactory.getCurrentSession();

			List<InternalAccount> results = (List<InternalAccount>)session.createCriteria(InternalAccount.class) 
					.add( Restrictions.eq("username", username ) ) 
					.list();
			logger.info("Login Attempt User -- Searched-- Username " + username); 
			if(results.size() == 0){
				return null;
			}else if(results.size() >= 1){
				System.out.println(results.get(0).toString());
				System.out.println("address = "+results.get(0).getAddress());
				return results.get(0);
			}
			return null;
		}
		
		@Override
		public String findRecoveryAccount(String username, String email, String tel, String sec_question, String sec_answer) {
			// TODO Auto-generated method stub
			Session session = sessionFactory.getCurrentSession();

			List<InternalAccount> results = (List<InternalAccount>)session.createCriteria(InternalAccount.class) 
					.add( Restrictions.eq("username", username ) ) 
					.add( Restrictions.eq("email", email ) )
					.add( Restrictions.eq("telephone", tel ) )
					.add( Restrictions.eq("securityquestion", sec_question ) )
					.add( Restrictions.eq("securityanswer", sec_answer ) )
					.list();
			logger.info("Login Attempt User -- Searched-- Username " + username); 
			if(results.size() >= 1)
			{
				System.out.println("INTERNAL");
				return "INTERNAL";
			}
			else if(results.size() == 0)
			{
				List<ExternalAccount> results_external = (List<ExternalAccount>)session.createCriteria(ExternalAccount.class) 
						.add( Restrictions.eq("username", username ) ) 
						.add( Restrictions.eq("email", email ) )
						.add( Restrictions.eq("telephone", tel ) )
						.add( Restrictions.eq("securityquestion", sec_question ) )
						.add( Restrictions.eq("securityanswer", sec_answer ) )
						.list();
				if(results_external.size() == 0)
				{
					System.out.println("NOT FOUND");
					return "NOT FOUND";
				}
				else
				{
					System.out.println("EXTERNAL");
					return "EXTERNAL";
				}
			}
			return "NOT FOUND";
		}
}
