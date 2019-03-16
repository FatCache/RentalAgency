package com.rentalagency.me.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/*
 * Abstract Class request which can be extended to represent 
 *  other types such as, parking, cleaning, garbage disposal 
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="request_table")
public abstract class Request implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int request_id;
	
	@Id
	@GenericGenerator(name="genUser",strategy="foreign", parameters = {@Parameter(name="property",value="user")})
	@GeneratedValue(generator="genUser")
	private int user_id;
	
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	private boolean status;
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name="user_id")
	@PrimaryKeyJoinColumn
	private User user;
	
	public int getRequest_id() {
		return request_id;
	}

	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	

}
