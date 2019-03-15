package com.rentalagency.me.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rentalagency.me.bean.ConnectionProvider;
import com.rentalagency.me.bean.LoginBean;
import com.rentalagency.me.model.DummyRequest;
import com.rentalagency.me.model.ParkingRequest;
import com.rentalagency.me.model.ParkingRequest.rowSpot;
import com.rentalagency.me.model.Request;
import com.rentalagency.me.model.SimpleMessage;
import com.rentalagency.me.model.SingleRequest;
import com.rentalagency.me.model.User;
import com.rentalagency.me.model.UserAccount;

public class LoginDAO extends DAO{
	
	private final static Logger log = LoggerFactory.getLogger(LoginDAO.class);

	
	public void create(UserAccount ua) {
		try {
			begin();
			User us = new User();
			us.setName("Created By: "  + ua.getUsername());
			
			
			ua.setUser(us);
			us.setUserAccount(ua);	
			
			SingleRequest sq = new SingleRequest();
			sq.setDescription("I wasn't adding the object back to maintain the damn graph!");
			sq.setUser(us);
			us.setSimplerequest(sq);
			
			DummyRequest dr = new DummyRequest();
			DummyRequest dr2 = new DummyRequest();			
			dr.setDescribeMe("A job I want");
			dr2.setDescribeMe("Its almost here");
			
			dr.setUser(us);
			dr2.setUser(us);
			
			us.getDummyRequest().add(dr);
			us.getDummyRequest().add(dr2);
			
			
			
			getSession().persist(ua);
			commit();
			
			
			
//			begin();
			// ZombieCode
//			ParkingRequest prq = new ParkingRequest();
//			prq.setDescription("One of Many Request: " + ua.getUsername() );
//			prq.setRsp(rowSpot.NINE);
//			prq.setEndTime(new Date());
//			
//			DummyRequest dr = new DummyRequest();
//			dr.setDescribeMe("A job I want");
//			
//			User ux = (User) getSession().load(User.class, new Integer(0));
//			
//			ux.getDummyRequest().add(dr);
//			ux.getRequestSet().add(prq);	
//			
//			getSession().update(ux);
			

//			commit();
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
}
