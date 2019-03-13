package com.rentalagency.me.model;

import java.io.Serializable;
import java.util.List;

/*
 * Generic User Concrete Class
 */
public class User implements Serializable {
	
	public int user_id;
	private String name;
	private User Role;
	private List<Message> messages;
	
	public enum Role {
		MANAGER, REGULAR, ADMIN, GUEST
	}
	
	public User(String name) {
		this.name= name;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getRole() {
		return Role;
	}

	public void setRole(User role) {
		Role = role;
	}
	
	
	
	
	 

}
