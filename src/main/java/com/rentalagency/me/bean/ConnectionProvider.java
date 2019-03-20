package com.rentalagency.me.bean;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Class which returns a single instance connection to the database
 * 
 * Change CONNECTION_URL, USERNAME, PASSWORD to match your configuration
 * of JDBC connection 
 * @author abdusamed
 *
 */
public class ConnectionProvider implements Provider {

	private static Connection con = null;
	
	static {
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
		} catch (Exception e) {
		}
	}

	public static Connection getCon() {
		return con;
	}

}
