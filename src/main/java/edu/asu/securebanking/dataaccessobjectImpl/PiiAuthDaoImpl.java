package edu.asu.securebanking.dataaccessobjectImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.securebanking.dataaccessobject.PiiAuthDao;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.PiiAuth;

@Repository
@SuppressWarnings({"unchecked", "rawtypes"})
@Transactional
public class PiiAuthDaoImpl implements PiiAuthDao {
	
	@Autowired private SessionFactory sessionFactory;
	
	private static final Logger logger = Logger.getLogger(PiiAuthDaoImpl.class);

    public  SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public  void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


	@Override
	public void save(PiiAuth piiAuth) throws InternalException {
		// TODO Auto-generated method stub
		System.out.println("Entered DaOImpl!");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("getpiireq");
		query.setParameter("userid", piiAuth.getUserid());
		System.out.println("size of list is: "+query.list().size());
		if (!(query.list().size() == 0))
			throw new InternalException("User already in Critical Transaction Queue!");
		else{
			session.save(piiAuth);
		}
		  logger.info("PiiAuth -- Created-- User Id " + piiAuth.getUserid()); 
	}

	@Override
	public List<PiiAuth> getallPiiAuth() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("getallpiireqs");

		List list = query.list();
		 logger.info("All PiiAuth -- Searched-- No of records " + list.size()); 
		 
		return list;
	}

	@Override
	public void authorize(int id, String msg) throws InternalException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query1 = session.getNamedQuery("updateauthorization");
		query1.setParameter("userid", id);
		query1.setParameter("isauthorized", msg);
		int result = query1.executeUpdate();
		 logger.info("PiiAuth -- Authorized-- User Id " + id); 
		System.out.println("Rows affected: "+ result);
		
	}

}
