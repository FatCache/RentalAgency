package com.rentalagency.me.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
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

/**
 * Data persistence logic pertaining to most of the web application behavior
 * Majority of the queries listed here relate to retrieving the data to be used
 * to populate view page
 * 
 * The class makes use of Hibernate Criteria and projection
 * @author abdusamed
 */
public class QueryDAO extends DAO {
	
	// Logger associated wit this class
	private final static Logger log = LoggerFactory.getLogger(QueryDAO.class);
	
	/**
	 * Retrieves list of Users without Manager Role
	 * @return a list of User
	 */
	public List<User> getListOfUserAccount() {
		List<User> userList = null;
		try {
			begin();
			Criteria userCriteria = getSession().createCriteria(User.class);
			userCriteria.add(Restrictions.ne("role", Role.MANAGER));
			userList = (List<User>) userCriteria.list();
			
		} catch(HibernateException e) {
			log.warn("Unable to retrieve accounts");
			rollback();
		}finally {
			close();
		}
		return userList;
	}
	
	/**
	 * Returns a list of User Accounts via User table who are not Manager 
	 * Manager is not returned because a manager is only restricted to delete
	 * users below it, which in our case is `REGULAR` only
	 * @return list of filtered UserAccount
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
		
	
	/**
	 * An example of using HQL query to retrieve data instead of using standard 
	 * SQL or Hibernate Criteria
	 * 
	 * @param id the User object to retrieve using id
	 * @return returns the user
	 */
	public User getUserById(int id) {
		User user = null;
		try {
			begin();
			// HQL in action ...
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
	
	/**
	 * Retrieves a list of ParkingRequest associated with the User ID
	 * Logic first receives a list of Parking request, it filters it down
	 * to user_id matching it. Returns the list	
	 * @param userId targeted user_id
	 * @return a Collection of ParkingRequest with List Interface
	 */
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
	
	/**
	 * Retrieves a list of all request made so far. Is used by ManagerController
	 * @return a collection of all request. Note, it returns an abstract of request
	 * hence, future request extending Request can work with it
	 */
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

	

	/**
	 * Creation of new parking request. It storest the parking request object sent
	 * to it
	 * @param prq the parking request object sent from the controller
	 * @param user_id parking request associated with the Id
	 */
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
	
	
		
	/**
	 * Searches for a parking request tied to a user list & to the description
	 * It uses double restrictions to double down it
	 * @param user_id targetted user
	 * @param description description of the request to match.
	 * @return
	 */
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
	
	/**
	 * Deletes a request by ID only. Only one is deleted per call. 
	 * Note: deletes an object extending Request
	 * @param request_id the Id of the Request object
	 */
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
	
	/**
	 * Retrieves one parking request object using request id only.
	 * @param request_id parking request Id
	 * @return returns a complete Parking Request Object
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
	
	
	/**
	 * View all users registered in the system
	 * @return Collection of User
	 */
	public List<User> getListOfUsers() {
		List<User> userList = null;
		try {
			begin();

			Query cr = getSession().createQuery("FROM User");			
			userList = (List<User>) cr.list();			
			
		} catch(HibernateException e) {
			log.warn("Unable to modify user account");
			rollback();
		}finally {
			close();
		}
		
		return userList;
		
	}
	

	/**
	 * Retrieval of UserAccount collection for JSON
	 * 
	 * Method demonstrating the use of Hibernate criteria to recieve
	 * object used for JSON only. Filter is imposed to get only
	 * Manager and only two attributes of UserAccount object,
	 * Username & User Id. The collection is queried and returned back
	 *  
	 * @return Collection of filtered UserAccount
	 */
	public List<UserAccount> getListOfUserAccountRegularJSON() {
		List<UserAccount> userList = null;
		try {
			begin();
			Criteria userCriteria = getSession().createCriteria(UserAccount.class);
			Projection p1 = Projections.property("username");
			Projection p2 = Projections.property("user_id");
			
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


}
