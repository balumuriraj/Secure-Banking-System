package edu.asu.securebanking.dataaccessobjectImpl;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import edu.asu.securebanking.dataaccessobject.CreateExternalAccountDao;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.ExternalAccount;


@Repository
@Transactional
public class CreateExternalAccountDaoImpl  implements CreateExternalAccountDao {

	private static final Logger logger = Logger.getLogger(CreateExternalAccountDaoImpl.class);
	
	@Autowired private SessionFactory sessionFactory;

	@Override

	/**
	 * @Transactional annotation below will trigger Spring Hibernate transaction manager to automatically create
	 * a hibernate session. See src/main/webapp/WEB-INF/servlet-context.xml
	 */
	public String save(ExternalAccount externalAccount) {
		String errors = "";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findexternaluserbyusername");
		query.setParameter("username", externalAccount.getUsername());
		if (!(query.list().size() == 0)){
			errors = errors + " Username already exists; ";
			System.out.println("Username already exists;");
		}
		
		Query intquery = session.getNamedQuery("findinternaluserbyusername");
		intquery.setParameter("username", externalAccount.getUsername());
		if (!(intquery.list().size() == 0)){
			errors = errors + " Username already exists; ";
			System.out.println("Username already exists;");
		}
		
		Query query1 = session.getNamedQuery("findexternaluserbyssn");
		query1.setParameter("ssn", externalAccount.getSsn());
		if (!(query1.list().size() == 0)){
			errors = errors + " SSN already exists; ";
			System.out.println("SSN already exists;");
		}
		
		Query query2 = session.getNamedQuery("findexternaluserbytelephone");
		query2.setParameter("telephone", externalAccount.getTelephone());
		if (!(query2.list().size() == 0)){
			errors = errors + " Phone number already exists; ";
			System.out.println("Phone already exists;");
		}
		
		Query query3 = session.getNamedQuery("findexternaluserbyemail");
		query3.setParameter("email", externalAccount.getEmail());
		if (!(query3.list().size() == 0)){
			errors = errors + " Email already exists; ";
			System.out.println("Email already exists;");
		}
		
		if(errors == ""){
			session.save(externalAccount);
			System.out.println("No errors;");
		}
		
		logger.info("ExternalAccount -- Created-- Username " + externalAccount.getUsername()); 
		
		return errors;
	}

	@Override
	public void update(ExternalAccount externalAccount) {
		Session session = sessionFactory.getCurrentSession();
		session.update(externalAccount);
		logger.info("ExternalAccount -- Updated-- Username " + externalAccount.getUsername()); 

	}

	@Override
	public void delete(ExternalAccount externalAccount) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(externalAccount);
		logger.info("ExternalAccount -- Deleted-- Username " + externalAccount.getUsername()); 

	}

	@Override
	@Transactional(rollbackFor=InternalException.class)
	public ExternalAccount findAccountByName(String username) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findexternaluserbyusername");
		query.setParameter("username", username);
		System.out.println("size of list is: "+query.list().size());
		if (query.list().size() == 0)
			return null;
		List<?> list = query.list();
		
		logger.info("ExternalAccount -- Searched-- Username " + username); 
		
		return (ExternalAccount)list.get(0);
	}


	public  SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public  void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override

	public List<ExternalAccount> getAllExternalUserAccounts() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		Query query = session.getNamedQuery("getallexternalusers");

		List list = query.list();
		
		logger.info("Searched all External User Accounts -- Number of records -- " +  list.size()); 
		
		return list;
	}

	public List<ExternalAccount> getdetails() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("getallexternalusers");

		List list = query.list();
		
		logger.info("Searched all External User Accounts -- Number of records -- " +  list.size()); 
		
		return list;

	}

	@Override
	public ExternalAccount findUserByid(int id) throws InternalException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findexternaluser");
		query.setParameter("userid", id);
		System.out.println("size of list is: "+query.list().size());
		if (query.list().size() == 0)
			//throw new InternalException("User ID does not exist in the database!");
			return null;
		List<?> list = query.list();
		
		logger.info("Searched External User Account -- UserId -- " +id); 
		
		return (ExternalAccount)list.get(0);
	}

	@Override
	public List<ExternalAccount> getpiireq() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		Query query = session.createQuery("Select userid from PiiAuth where isauthorized = 'yes'");
		List plist =  query.list();
		System.out.println("size of list is: "+query.list().size());

		if(query.list().size() == 0){
			return null;
		}
		else{
			Query query1 = session.createQuery("FROM ExternalAccount WHERE userid IN (:piilist)");
			query1.setParameterList("piilist", plist);
			List<ExternalAccount> externalAccountList = query1.list();
			System.out.println("size of list is: "+query1.list().size());

			logger.info("Searched all External User Account -- Number of records -- " +  externalAccountList.size()); 
			
			return externalAccountList;
		}

	}

	@Override
	public List<ExternalAccount> getccreq() {
		Session session = sessionFactory.getCurrentSession();

		Query query = session.createQuery("Select accountNo from CreditcardRequests where approved = 'no'");
		List clist =  query.list();
		System.out.println("size of ccreq list is: "+query.list().size());

		if(query.list().size() == 0){
			System.out.println("returning null");
			return null;
		}
		else{
			Query query1 = session.createQuery("FROM ExternalAccount WHERE accountNo IN (:cclist)");
			query1.setParameterList("cclist", clist);
			List<ExternalAccount> externalAccountList = query1.list();
			System.out.println("size of list is: "+query1.list().size());

			logger.info("Searched all Credit Cards Requests -- Number of records -- " +  query1.list().size());
			
			return externalAccountList;
		}
	}

}
