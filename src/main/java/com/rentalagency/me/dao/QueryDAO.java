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
import com.rentalagency.me.model.UserAccount;
import com.rentalagency.me.model.ParkingRequest.colSpot;
import com.rentalagency.me.model.ParkingRequest.rowSpot;
import com.rentalagency.me.model.Request;

public class QueryDAO extends DAO {
	
	private final static Logger log = LoggerFactory.getLogger(QueryDAO.class);
	
	// Retrieve a list of UserAccount
	public List<UserAccount> getListOfUserAccount() {
		List<UserAccount> userList = null;
		try {
			begin();
			Criteria cr = getSession().createCriteria(UserAccount.class);
			
			userList = (List<UserAccount>) cr.list();
			
			commit();			
			
		} catch(HibernateException e) {
			log.warn("Unable to modify user account");
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
	
	
	/*
	 * Modify User Account password
	 */
	public void modifyUserAccountById(int i, String newPassword) {
		try {
			begin();
			Criteria cr = getSession().createCriteria(UserAccount.class);
			Criterion userModify = Restrictions.eq("user_id", new Integer(i));
			cr.add(userModify);
			cr.setMaxResults(1);
			
			UserAccount user = (UserAccount) cr.uniqueResult();

			user.setPassword(newPassword);
			
			getSession().update(user);
			
			commit();			
			
		} catch(HibernateException e) {
			log.warn("Unable to modify user account");
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
	
	public void deleteParkingRequestById(int request_id) {
		
		try {
			begin();
			Criteria cr = getSession().createCriteria(ParkingRequest.class)
					.add(Restrictions.eq("request_id", request_id));			
			
			cr.setMaxResults(1);
			ParkingRequest pr = (ParkingRequest) cr.uniqueResult();

			if(pr != null) {
				getSession().delete(pr);
				log.info("Delete Performed");
			}
			
				
			
		} catch(HibernateException e) {
			log.warn("Unable to modify user account");
			rollback();
		}finally {
			close();
		}
	}
	
	/*
	 * Retrieve any parking request by Id;
	 * Usecase: Management 
	 */
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
