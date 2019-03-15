package com.rentalagency.me.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "user_table")
public class User  {
	
	/*
	 * Check what happens if user_id changed in child object. Does it still work?
	 */
	
	@Id
	@GenericGenerator(name="generator",strategy="foreign", 
					  parameters = {@Parameter(name="property",value="userAccount")})
	@GeneratedValue(generator="generator")
	private int user_id;
	
	private String name; 
	private Role role;
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private UserAccount userAccount;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy="user")
	private Set<Request> requestSet = new HashSet<Request>();

	
	public enum Role {
		REGULAR,MANGER
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}


	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}	
	
	
	 

}
