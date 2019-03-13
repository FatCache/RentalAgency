package com.rentalagency.me.model;

import java.util.Date;

/*
 * Abstract Class request which can be extended to represent 
 *  other types such as, parking, cleaning, garbage disposal 
 */
public abstract class Request {

	private int userId;
	private boolean status;
	private Date createTime;
	private String description;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

}
