package edu.asu.securebanking.dataaccessobjectImpl;



import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.securebanking.dataaccessobject.PassKeyDao;
import edu.asu.securebanking.model.PassKey;

@Repository
@Transactional
public class PassKeyDaoImpl implements PassKeyDao{

	@Autowired private SessionFactory sessionFactory;
	
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public  void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
	@Override
	public void save(PassKey passKey) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(passKey);
	}

	@Override
	public void update(PassKey passKey) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(passKey);
		
	}

	@Override
	public void delete(String passKey) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		PassKey passkeyModel = findPassKey(passKey);
		if(passkeyModel != null)
			session.delete(passkeyModel);
	}

	@Override
	public PassKey findPassKey(String passKey) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		System.out.println("checkB2");
		Query query = session.getNamedQuery("findpasskey");
		System.out.println("checkB3");
		query.setParameter("passkey",passKey);
		System.out.println("checkB4");
		if (query.list().isEmpty())
		{
			System.out.println("checkB6");
			return null;
		}
		else
		{
			System.out.println("checkB5");
			List<?> list = query.list();
			return (PassKey)list.get(0);
		}
	}

	

}
