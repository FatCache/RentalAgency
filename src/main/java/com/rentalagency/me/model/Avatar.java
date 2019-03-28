package com.rentalagency.me.model;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="avatar_table")
public class Avatar implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer avatarId;
	
	private String name;
	
	@Column(nullable=false) 
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Lob
    @Column(nullable=false, columnDefinition="mediumblob")
	private byte[] image;

	public Avatar() {
	}

	public Avatar(byte[] image) {
		this.image = image;
	}

	public Integer getAvatarId() {
		return this.avatarId;
	}

	public void setAvatarId(Integer avatarId) {
		this.avatarId = avatarId;
	}

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
