package com.rentalagency.me.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rentalagency.me.bean.ConnectionProvider;
import com.rentalagency.me.bean.LoginBean;
import com.rentalagency.me.model.User;
import com.rentalagency.me.model.User.Role;
import com.rentalagency.me.model.UserAccount;

/**
 * Data persistence login used to handle the user authentication.
 * The database queries are handled using Hibernate session 
 * 
 * Data persistence tied to creation and modification to user account
 * for example, change of password is handled here.
 * @author abdusamed
 * @
 *
 */
public class LoginDAO extends DAO{
	
	private final static Logger log = LoggerFactory.getLogger(LoginDAO.class);

	/**
	 * User account is created with a new user with it. 
	 * @param ua Useraccount object sent to be stored
	 * @param role the role of new user to be associated with this account
	 */
	public void create(UserAccount ua,String role) {
		try {
			begin();
			User us = new User();
			us.setName(ua.getUsername());
			us.setRole(Role.valueOf(role));
			
			ua.setUser(us);
			us.setUserAccount(ua);	
						
			getSession().persist(ua);
			commit();			
			
			log.info("Account creation Successful");
		} catch (HibernateException e) {
			log.warn("Unable To Add Message, Failure");
			rollback();
		} finally {
			close();
		}
	}
	
	/**
	 * Static method used only for verification of the user. A temp object is 
	 * retrieved and check in the database if it exist.
	 * 
	 * Instead of using Hibernate criteria which is used frequently in this project, 
	 * a demonstration of using standard SQL is shown here.
	 * @param bean object containing attribute email [username] & password to check
	 * @return returns bool true if user exist otherwise false.
	 */
	public static boolean validate(LoginBean bean){  
		boolean status=false;  
		try {  
			Connection con = ConnectionProvider.getCon();  
			              
			PreparedStatement ps = con.prepareStatement(  
			    "select * from useraccount_table where username=? and password=?");  
			  
			ps.setString(1,bean.getEmail());  
			ps.setString(2, bean.getPass());  
			              
			ResultSet rs=ps.executeQuery();  
			status=rs.next();  
  
		}catch(Exception e){
			log.warn("Error occured when trying to retrieve the user");
			e.printStackTrace();
		}  
		  
		return status;  
		}
		
	
	/**
	 * Retrives the useraccount using criteria & restrictions.
	 * @param username
	 * @return object userAccount
	 */
	public UserAccount getUserAccountByUserName(String username) {
		UserAccount ua = null;
		try {
			begin();
			Criteria cr = getSession().createCriteria(UserAccount.class);
			Criterion userModify = Restrictions.eq("username", username);
			cr.add(userModify);
			cr.setMaxResults(1);
			
			ua = (UserAccount) cr.uniqueResult();
			
			
		} catch(HibernateException e) {
			log.warn("Unable to modify user account");
			rollback();
		}finally {
			close();
		}
		return ua;		
	}

	/**
	 * Logic pertaining to modification of useraccount password & username
	 * @param user_id the account ID to be modified
	 * @param newPassword the new password to be used
	 * @param newUsername the new username to be associated with the same account
	 */
	public void modifyUserAccountById(int user_id, String newPassword, String newUsername) {
		try {
			begin();
			Criteria cr = getSession().createCriteria(UserAccount.class);
			Criterion userModify = Restrictions.eq("user_id", new Integer(user_id));
			cr.add(userModify);
			cr.setMaxResults(1);
			
			UserAccount user = (UserAccount) cr.uniqueResult();
			user.setUsername(newUsername);
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
	
	/**
	 * Deletes user account along with all data associated with it. Makes uses of 
	 * cascade functionality. 
	 * @param user_id the user account to be deleted by ID
	 */
	public void deleteUserAccountById(int user_id) {
		try {
			begin();
			Criteria cr = getSession().createCriteria(UserAccount.class);
			Criterion userModify = Restrictions.eq("user_id", new Integer(user_id));
			cr.add(userModify);
			cr.setMaxResults(1);
			
			UserAccount user = (UserAccount) cr.uniqueResult();
		
			if(user != null) {
				getSession().delete(user);
			}
			
			
			commit();			
			
		} catch(HibernateException e) {
			log.warn("Unable to modify user account");
			rollback();
		}finally {
			close();
		}
	}
	
	/**
	 * Used for batch delete accounts
	 * @param user_id
	 */
	public void batchDeleteAccounts(String username) {
		try {
			begin();
			Criteria cr = getSession().createCriteria(UserAccount.class);
			Criterion userModify = Restrictions.eq("username",username);
			cr.add(userModify);
			
			
			List<UserAccount> user = (List<UserAccount>) cr.list();
		
			for(UserAccount ua : user) {
				getSession().delete(ua);
			}
			
			
			commit();			
			
		} catch(HibernateException e) {
			log.warn("Unable to modify user account");
			rollback();
		}finally {
			close();
		}
	}
}
