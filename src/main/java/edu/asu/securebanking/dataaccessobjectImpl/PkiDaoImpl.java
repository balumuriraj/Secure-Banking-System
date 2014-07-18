package edu.asu.securebanking.dataaccessobjectImpl;

import java.security.KeyPair;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.Blob;

import edu.asu.securebanking.dataaccessobject.PkiDao;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.ExternalUserTransaction;
import edu.asu.securebanking.model.InternalUserTransaction;
import edu.asu.securebanking.model.Pki;

@Repository
@Transactional
public class PkiDaoImpl implements PkiDao{

	@Autowired private SessionFactory sessionFactory;
	
	public  SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public  void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void save(Pki pki) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(pki);
	}

	@Override
	public Pki findPKI(String transId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<Pki> list = session.createCriteria(Pki.class).add( Restrictions.eq("transId", transId)).list(); 
		
		if(list.size() == 0)
			throw new InternalException("Invalid Certificate");
		else
			return list.get(0);
	}

	@Override
	public Blob createKeyBlob(KeyPair keyPair) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		byte[] byteBlob = keyPair.getPublic().getEncoded();
		Blob blob = (Blob) Hibernate.getLobCreator(session).createBlob(byteBlob);
		return blob;
	}

	@Override
	public void update(Pki pki) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(pki);
	}

	@Override
	public void delete(Pki pki) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(pki);
	}
	
}
