package com.rentalagency.me.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity	
@Table(name="singlerequest_table")
public class SingleRequest {
	
	@Id
	@GenericGenerator(name="genSingle",strategy="foreign", parameters = {@Parameter(name="property",value="user")})
	@GeneratedValue(generator="genSingle")
	@Column(name="user_id") // <- IM ASSUMING THE VALUES NAME SHOULD MATCH WITH ATTRIBUTE OF USER OBJECT
	private int user_id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private User user;
	
	private String description;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

	
	
}
