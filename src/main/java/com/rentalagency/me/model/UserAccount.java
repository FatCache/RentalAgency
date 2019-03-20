package com.rentalagency.me.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * UserAccount stores the login credential once created
 * and builds the association with it. For example, a user 
 * object is assigned to it. The mapping logic of new user object 
 * is carried in LoginDAO classes.
 * 
 * Once a user_account is deleted, everything associated with 
 * it is cascaded throughout the database. The credentials are 
 * kept in the table called "useraccount_table".
 *  
 * @author abdusamed
 *
 */
@Entity
@Table(name="useraccount_table")
public class UserAccount {
	
	@Id
	@Column(name="user_id",unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int user_id;
	
	private String username;
	private String password;
	
	//UserAccount is parent to child -> User association. "
	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy = "userAccount")
    @JsonIgnore // Required to avoid recursive loop during JSON marshalling
	private User user;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	

}
