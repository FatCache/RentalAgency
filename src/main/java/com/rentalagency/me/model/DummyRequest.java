package com.rentalagency.me.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="dummyrequest_table")
public class DummyRequest {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int dummyRequest_id;
	
	
	
	public int getDummyRequest_id() {
		return dummyRequest_id;
	}

	public void setDummyRequest_id(int dummyRequest_id) {
		this.dummyRequest_id = dummyRequest_id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	private String describeMe;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescribeMe() {
		return describeMe;
	}

	public void setDescribeMe(String describeMe) {
		this.describeMe = describeMe;
	}
	
	

}
