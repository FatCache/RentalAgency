package com.rentalagency.me.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.rentalagency.me.bean.ConnectionProvider;
import com.rentalagency.me.bean.LoginBean;

import com.rentalagency.me.model.ParkingRequest;
import com.rentalagency.me.model.ParkingRequest.colSpot;
import com.rentalagency.me.model.ParkingRequest.rowSpot;
import com.rentalagency.me.model.Request;
import com.rentalagency.me.model.SimpleMessage;

import com.rentalagency.me.model.User;
import com.rentalagency.me.model.UserAccount;

import org.junit.Before;
import org.junit.Test;

public class LoginDAO extends DAO{
	
	private final static Logger log = LoggerFactory.getLogger(LoginDAO.class);


	public void create(UserAccount ua) {
		try {
			begin();
			User us = new User();
			us.setName("Created By: "  + ua.getUsername());
			
			ua.setUser(us);
			us.setUserAccount(ua);	
					
			ParkingRequest prq = new ParkingRequest();
			prq.setDescription("One of Many Request: " + ua.getUsername() );
			prq.setRsp(rowSpot.NINE);
			prq.setEndTime(new Date());
			prq.setUser(us);

			us.getRequestSet().add(prq);			
			
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
	
	

	public static boolean validate(LoginBean bean){  
		boolean status=false;  
		try{  
		Connection con = ConnectionProvider.getCon();  
		              
		PreparedStatement ps = con.prepareStatement(  
		    "select * from usertable where email=? and pass=?");  
		  
		ps.setString(1,bean.getEmail());  
		ps.setString(2, bean.getPass());  
		              
		ResultSet rs=ps.executeQuery();  
		status=rs.next();  
  
		}catch(Exception e){}  
		  
		return status;  
		}
	

	/*
	 * Modify User Account password
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
}
