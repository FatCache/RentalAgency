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



/*
 * One SimpleMessage can have one SimpleAccount
 * SimpleMessage -> SimpleAccount
 */
@Entity
@Table(name="simplemessage")
public class SimpleMessage {
	
	@Id
	@Column(name="message_id",unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int message_id;
	
	private String content;

	private Status status;
	
	
	@OneToOne(fetch = FetchType.LAZY,mappedBy="simplemessage",cascade = CascadeType.ALL)
	private SimpleAccount sm;
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public enum Status{
		READ,UNDREAD
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getMessage_id() {
		return message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	public SimpleAccount getSm() {
		return sm;
	}

	public void setSm(SimpleAccount sm) {
		this.sm = sm;
	}
	
	
	
	
	
	
}
