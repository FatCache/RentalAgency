package com.rentalagency.me.model;

import java.util.Date;

/*
 * One to Many Relationship
 * One User can have many Message assigned
 */
public class Message {

	private String message;
	private Date time;
	private User sentBy;
	private User sentTo;

	// false = Unread
	private boolean status;

	public Message(String message) {
		this.message = message;
		this.status = false;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public User getSentBy() {
		return sentBy;
	}

	public void setSentBy(User sentBy) {
		this.sentBy = sentBy;
	}

	public User getSentTo() {
		return sentTo;
	}

	public void setSentTo(User sentTo) {
		this.sentTo = sentTo;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
