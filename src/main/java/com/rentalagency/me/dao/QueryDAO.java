package com.rentalagency.me.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rentalagency.me.model.ParkingRequest;
import com.rentalagency.me.model.User;
import com.rentalagency.me.model.User.Role;
import com.rentalagency.me.model.UserAccount;
import com.rentalagency.me.model.ParkingRequest.colSpot;
import com.rentalagency.me.model.ParkingRequest.rowSpot;
import com.rentalagency.me.model.Request;

public class QueryDAO extends DAO {
	
	private final static Logger log = LoggerFactory.getLogger(QueryDAO.class);
	
	/*
	 * Retrieves list of Users who do not belong to Manager
	 * 
	 */
	public List<UserAccount> getListOfUserAccount() {
		List<UserAccount> userList = null;
		try {
			begin();
			Criteria userCriteria = getSession().createCriteria(User.class);
			userCriteria.add(Restrictions.ne("role", Role.MANAGER));
			userList = (List<UserAccount>) userCriteria.list();
			
		} catch(HibernateException e) {
			log.warn("Unable to retrieve accounts");
			rollback();
		}finally {
			close();
		}
		return userList;
	}
	
	/*
	 * Returns a list of User Accounts via User table who are not Manager 
	 * Because a manager cannot delete himself directly
	 */
	public List<UserAccount> getListOfUserAccountRegular() {
		List<UserAccount> userList = null;
		try {
			begin();
			Criteria userCriteria = getSession().createCriteria(UserAccount.class);
			userCriteria.createCriteria("user","u");			
			userCriteria.add(Restrictions.ne("u.role", Role.MANAGER));			
			
			userList = (List<UserAccount>) userCriteria.list();
			
			
		} catch(HibernateException e) {
			log.warn("Unable to retrieve accounts");
			rollback();
		}finally {
			close();
		}
		return userList;
	}
	
	// Retrieve a list of Users
	public List<User> getListOfUsers() {
		List<User> userList = null;
		try {
			begin();

			Query cr = getSession().createQuery("FROM User");			
			userList = (List<User>) cr.list();
			
			commit();			
			
		} catch(HibernateException e) {
			log.warn("Unable to modify user account");
			rollback();
		}finally {
			close();
		}
		
		return userList;
		
	}
	
	
	/*
	 * Uses SQL Query to Retrieve Data
	 */
	public User getUserById(int id) {
		User user = null;
		try {
			begin();

			Query q = getSession().createQuery("FROM User U WHERE U.user_id = :user_id")
					.setParameter("user_id", id);
			q.setMaxResults(1);
			user = (User) q.uniqueResult();	
			
		} catch(HibernateException e) {
			log.warn("Unable to modify user account");
			rollback();
		}finally {
			close();
		}
		
		return user;
		
	}
	
	// Retrieve a list of ParkingRequest by Id	
	public List<ParkingRequest> getParkingRequestListByUserId(int userId) {
		List<ParkingRequest> parkingRequestList = null;
		try {
			begin();
			
			Criteria cr = getSession().createCriteria(ParkingRequest.class);
			Criterion useCriteria = Restrictions.eq("user_id", userId);
			cr.add(useCriteria);
			
			parkingRequestList = (List<ParkingRequest>) cr.list();			
			
		} catch(HibernateException e) {
			log.warn("Unable to retrieve user account list");
			rollback();
		}finally {
			close();
		}
		return parkingRequestList;
	}
	
	// List of Request
	public List<Request> getRequestList() {
		List<Request> parkingRequestList = null;
		try {
			begin();
			
			Criteria cr = getSession().createCriteria(Request.class);

			parkingRequestList = (List<Request>) cr.list();			
			
		} catch(HibernateException e) {
			log.warn("Unable to retrieve user account list");
			rollback();
		}finally {
			close();
		}
		return parkingRequestList;
	}

	

	
	public void submitRequestById(ParkingRequest prq, int user_id) {
		try {
			begin();
			
			Criteria cr = getSession().createCriteria(UserAccount.class);
			Criterion userModify = Restrictions.eq("user_id", new Integer(user_id));
			cr.add(userModify);
			cr.setMaxResults(1);
			
			UserAccount useraccount = (UserAccount) cr.uniqueResult();
			
			User user = useraccount.getUser();
			
			prq.setUser(user);
			user.getRequestSet().add(prq);
			
			getSession().update(user);
			
			commit();		
			
		} catch(HibernateException e) {
			log.warn("Unable to retrieve user account list");
			rollback();
		}finally {
			close();
		}
	}
	
	
		
	
	public ParkingRequest getParkingRequestByUserIdByDescription(int user_id, String description) {
		ParkingRequest rq = null;
		try {
			begin();
			Criteria cr = getSession().createCriteria(ParkingRequest.class)
					.add(Restrictions.eq("description", description))
					.add(Restrictions.eq("user_id", user_id));
			
			
			cr.setMaxResults(1);
			
			rq = (ParkingRequest) cr.uniqueResult();
				
			
		} catch(HibernateException e) {
			log.warn("Unable to modify user account");
			rollback();
		}finally {
			close();
		}
		
		return rq;
	}
	
	public void deleteRequestById(int request_id) {
		
		try {
			begin();
			Criteria cr = getSession().createCriteria(Request.class)
					.add(Restrictions.eq("request_id", request_id));			
			
			cr.setMaxResults(1);
			Request pr = (Request) cr.uniqueResult();

			if(pr != null) {
				getSession().delete(pr);
				log.info("Delete Performed");
			}
			commit();
			
				
			
		} catch(HibernateException e) {
			e.printStackTrace();
			log.warn("Unable to Delete Post");
			rollback();
		}finally {
			close();
		}
	}
	
	// Checks if parking request retrieve is expected and not null
	public ParkingRequest getParkingRequestById(int request_id) {
		ParkingRequest pr = null;
		try {
			begin();
			Criteria cr = getSession().createCriteria(ParkingRequest.class)
					.add(Restrictions.eq("request_id", request_id));			
			
			cr.setMaxResults(1);
			pr = (ParkingRequest) cr.uniqueResult();	
			
		} catch(HibernateException e) {
			log.warn("Unable to modify user account");
			rollback();
		}finally {
			close();
		}
		return pr;
	}
	


}
