package edu.asu.securebanking.dataaccessobjectImpl;

import java.util.List;

import javassist.bytecode.Descriptor.Iterator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;

import edu.asu.securebanking.dataaccessobject.OTPDao;
import edu.asu.securebanking.model.OTP;
import edu.asu.securebanking.model.OTP;
import edu.asu.securebanking.model.OTP;

@Repository
@SuppressWarnings({"unchecked", "rawtypes"})
@Transactional
public class OTPDaoImpl  implements OTPDao {

	@Autowired private SessionFactory sessionFactory;
	
		

	    public  SessionFactory getSessionFactory() {
	        return sessionFactory;
	    }

	    public  void setSessionFactory(SessionFactory sessionFactory) {
	        this.sessionFactory = sessionFactory;
	    }

		@Override
		public void save(OTP OTP) {
			// TODO Auto-generated method stub
			Session session = sessionFactory.getCurrentSession();
			List<OTP> results = (List<OTP>)session.createCriteria(OTP.class) 
					.add( Restrictions.eq("userid", OTP.getuserid() ) ) 
					.add( Restrictions.eq("empid", OTP.getempid() ) ) 
					.list();
			if(results.size() == 0){
				session.save(OTP);
			}else{
				results.get(0).setotp(OTP.getotp());
				results.get(0).settime((java.sql.Timestamp)OTP.gettime());
				session.update(results.get(0));
			}
		}

		@Override
		public void update(OTP OTP) {
			// TODO Auto-generated method stub
			Session session = sessionFactory.getCurrentSession();
			session.update(OTP);
		}

		@Override
		public void delete(OTP OTP) {
			// TODO Auto-generated method stub
			Session session = sessionFactory.getCurrentSession();
			session.delete(OTP);
		}

		@Override
		public OTP findAccountByNameExternal(int userid, int empid) {
			// TODO Auto-generated method stub
			Session session = sessionFactory.getCurrentSession();

			List<OTP> results = (List<OTP>)session.createCriteria(OTP.class) 
					.add( Restrictions.eq("userid", userid ) ) 
					.add( Restrictions.eq("empid", empid ) ) 					
					.list();
			if(results.size() == 0){
				return null;
			}else if(results.size() >= 1){
				return results.get(0);
			}
			return null;
		}

}
