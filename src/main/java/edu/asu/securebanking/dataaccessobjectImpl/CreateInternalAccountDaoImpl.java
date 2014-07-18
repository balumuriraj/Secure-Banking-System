package edu.asu.securebanking.dataaccessobjectImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.securebanking.dataaccessobject.CreateInternalAccountDao;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.InternalAccount;

@Repository
@Transactional
public class CreateInternalAccountDaoImpl implements CreateInternalAccountDao{
	
	private static final Logger logger = Logger.getLogger(CreateInternalAccountDaoImpl.class);

	@Autowired private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public String save(InternalAccount internalAccount) {
		// TODO Auto-generated method stub
		String errors = "";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findinternaluserbyusername");
		query.setParameter("username", internalAccount.getUsername());
		if (!(query.list().size() == 0)){
			errors = errors + " Username already exists; ";
			System.out.println("Username already exists;");
		}
		
		Query extquery = session.getNamedQuery("findexternaluserbyusername");
		extquery.setParameter("username", internalAccount.getUsername());
		if (!(extquery.list().size() == 0)){
			errors = errors + " Username already exists; ";
			System.out.println("Username already exists;");
		}
		
		
		Query query1 = session.getNamedQuery("findinternaluserbyssn");
		query1.setParameter("ssn", internalAccount.getSsn());
		if (!(query1.list().size() == 0)){
			errors = errors + " SSN already exists; ";
			System.out.println("SSN already exists;");
		}
		
		Query query2 = session.getNamedQuery("findinternaluserbytelephone");
		query2.setParameter("telephone", internalAccount.getTelephone());
		if (!(query2.list().size() == 0)){
			errors = errors + " Phone number already exists; ";
			System.out.println("Phone already exists;");
		}
		
		Query query3 = session.getNamedQuery("findinternaluserbyemail");
		query3.setParameter("email", internalAccount.getEmail());
		if (!(query3.list().size() == 0)){
			errors = errors + " Email already exists; ";
			System.out.println("Email already exists;");
		}
		
		if(errors == ""){
			session.save(internalAccount);
			System.out.println("No errors;");
		}
		
		logger.info("Internal Account -- Created-- Username " + internalAccount.getUsername()); 
		
		return errors;
			
	}

	@Override
	public void update(InternalAccount internalAccount) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(internalAccount);
		
		logger.info("Internal Account -- Updated-- Username " + internalAccount.getUsername()); 

	}

	@Override
	public void delete(int employeeid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		InternalAccount user = (InternalAccount) session.get(InternalAccount.class, employeeid);
		if (user != null)
			session.delete(user);
		
		logger.info("Internal Account -- Deleted-- Employee Id " + employeeid); 

	}
	

	@Override
	public void deleteUser(InternalAccount internalAccount){
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
	
			session.delete(internalAccount);
			
			logger.info("Internal Account -- Deleted-- UserName " + internalAccount.getUsername()); 
	}

	@Override
	@Transactional(rollbackFor=InternalException.class)
	public InternalAccount findUserByid(int id) throws InternalException {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findinternaluser");
		query.setParameter("employeeId", id);
		System.out.println("size of list is: "+query.list().size());
		if (query.list().size() == 0)
			throw new InternalException("User ID does not exist in the database!");
		List<?> list = query.list();
		
		logger.info("Internal Account -- Searched-- Employee Id  " + id); 
		
		return (InternalAccount)list.get(0);
	}

	@Override
	public List<InternalAccount> getAllInternalUserAccounts() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("getallinternalusers");
		List list = query.list();
		List<InternalAccount> returnList = new ArrayList<InternalAccount>();
		
		int size = list.size();
		
		for(int i=0;i<size;i++)
		{
			InternalAccount test = (InternalAccount) list.get(i);
			
			
		
			
			
			if(!(test.getDeptid()==100))
			{
				
				returnList.add(test);
			}
			

			
		}
	

		
		
		logger.info("All Internal Account -- Searched-- Number of records " + list.size()); 
		
		return returnList;

	}

	@Override
	@Transactional(rollbackFor=InternalException.class)
	public void transferinternaluser(int id, int deptid) throws InternalException {
		// TODO Auto-generated method stub
		
		Session session = sessionFactory.getCurrentSession();
		Query query2 = session.getNamedQuery("findinternaluser");

		query2.setParameter("employeeId", id);
		
		System.out.println("(Transfer) size of list is: "+query2.list().size());
		if (query2.list().size() == 0)
		{
			throw new InternalException("User ID does not exist in the database!");
		} 
		
		else if (deptid == 0)
		{
			throw new InternalException("Please select a department!");
		}

		InternalAccount temp = (InternalAccount) query2.list().get(0);
		
		if (temp.getDeptid() == 100){
			throw new InternalException("Cannot transfer Corporate Level officials!");
		}
		else{
			Query query = session.getNamedQuery("findinternaluserbyidanddeptid");
			query.setParameter("employeeId", id);
			query.setParameter("deptid", deptid);
			System.out.println("size of list is: "+query.list().size());
			if (!(query.list().size() == 0))
			{
				throw new InternalException("User ID already exists in this Department!");
			} 
			else{
				Query query1 = session.getNamedQuery("transferinternaluser");
				/*Query query1 = session.createQuery("update InternalAccount set deptid = :deptid where employeeId = :employeeId");*/
				query1.setParameter("employeeId", id);
				query1.setParameter("deptid", deptid);
				int result = query1.executeUpdate();
				System.out.println("Rows affected: "+ result);
			}
		}
		
		logger.info("Internal Account -- Transferred-- Employee Id " + id); 
		
	}

	@Override
	@Transactional(rollbackFor=InternalException.class)
	public InternalAccount findUserByidanddeptid(int id, int deptid)
			throws InternalException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findinternaluserbyidanddeptid");
		query.setParameter("employeeId", id);
		query.setParameter("deptid", deptid);
		System.out.println("size of list is: "+query.list().size());
		if (query.list().size() == 0)
			throw new InternalException("User ID does not exist in this Department!");
		List<?> list = query.list();
		
		logger.info("Internal Account -- Searched-- Employee Id  " + id); 
		
		return (InternalAccount)list.get(0);
		
	
	}

	@Override
	public InternalAccount findUserByusername(String username)
			throws InternalException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findinternaluserbyusername");
		query.setParameter("username", username);
		System.out.println("size of list is: "+query.list().size());
		if (query.list().size() == 0)

       return null;
		else{
			List<?> list = query.list();
			logger.info("Internal Account -- Searched-- Username " + username); 
			return (InternalAccount)list.get(0);
			
		}

	}

	@Override
	public List<InternalAccount> getdetails() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("getallinternalusers");

		List list = query.list();
		logger.info("All Internal Account -- Searched-- No of Records " + list.size()); 
		return list;
	}

	@Override
	public void updatesalary(int id, double salary) throws InternalException {
		Session session = sessionFactory.getCurrentSession();
		Query query2 = session.getNamedQuery("findinternaluser");

		query2.setParameter("employeeId", id);
		
		System.out.println("(Transfer) size of list is: "+query2.list().size());
		if (query2.list().size() == 0)
		{
			throw new InternalException("User ID does not exist in the database!");
		} else{
			Query query1 = session.getNamedQuery("updatesalary");
			query1.setParameter("employeeId", id);
			query1.setParameter("salary", salary);
			int result = query1.executeUpdate();
			System.out.println("Rows affected: "+ result);
		}
		logger.info("Internal Account -- Salary Upadated -- Employee Id " + id); 
	}

}