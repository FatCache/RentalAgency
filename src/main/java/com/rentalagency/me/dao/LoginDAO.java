package com.rentalagency.me.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.rentalagency.me.bean.ConnectionProvider;
import com.rentalagency.me.bean.LoginBean;

public class LoginDAO {

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
