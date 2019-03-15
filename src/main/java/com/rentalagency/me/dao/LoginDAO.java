package com.rentalagency.me.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rentalagency.me.bean.ConnectionProvider;
import com.rentalagency.me.bean.LoginBean;
import com.rentalagency.me.model.User;
import com.rentalagency.me.model.UserAccount;

public class LoginDAO extends DAO{
	
	private final static Logger log = LoggerFactory.getLogger(LoginDAO.class);

	
	public void create(UserAccount ua) {
		try {
			begin();
			User us = new User();
			us.setName("Abdusamed");
			
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

	public static boolean validate(LoginBean bean){  
		boolean status=false;  
		try{  
		Connection con = ConnectionProvider.getCon();  
		              
		PreparedStatement ps = con.prepareStatement(  
		    "select * from usertable where email=? and pass=?");  
		  
		ps.setString(1,bean.getEmail());  
		ps.setString(2, bean.getPass());  
		
		System.out.format("Executing Query: %s\n", ps);
		              
		ResultSet rs=ps.executeQuery();  
		status=rs.next();  
		//con.close();            
		}catch(Exception e){}  
		  
		return status;  
		}
}
