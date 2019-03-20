package com.rentalagency.me.bean;


/**
 * Configuration required to establish a JDBC connection to the database 
 * @author abdusamed
 *
 */
public interface Provider {
	String DRIVER="com.mysql.jdbc.Driver";  
	String CONNECTION_URL="jdbc:mysql://localhost:3306/retailagency";  
	String USERNAME="root";  
	String PASSWORD="root";  
}
