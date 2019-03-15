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
@Table(name="simpleaccounttable")
public class SimpleAccount {
	 

	/*
	 * Generic generator used because we want to get id's from SimpleMessage [ Parent]
	 * of SimpleAccount class instead of letting this class generate ids on its own
	 * @Parameters is the bridge to the other table.
	 */
	@Id
	@GenericGenerator(name="gen",strategy="foreign", parameters = {@Parameter(name="property",value="simplemessage")})
	@GeneratedValue(generator="gen")
	@Column(name="message_id")
	private int message_id;
	
	private String simpleAccountDescription;
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private SimpleMessage simplemessage; // Property being referenced by 'mappedby'

	
	public SimpleMessage getSimplemessage() {
		return simplemessage;
	}

	public void setSimplemessage(SimpleMessage simplemessage) {
		this.simplemessage = simplemessage;
	}

	public int getMessage_id() {
		return message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	public String getSimpleAccountDescription() {
		return simpleAccountDescription;
	}

	public void setSimpleAccountDescription(String simpleAccountDescription) {
		this.simpleAccountDescription = simpleAccountDescription;
	}

	
	
	
}
