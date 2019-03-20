package com.rentalagency.me.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.engine.spi.CascadeStyle;

/*
 * One Useraccount is mapped to one User object
 * If the useraccount is deleted, along with all history tied to it
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
