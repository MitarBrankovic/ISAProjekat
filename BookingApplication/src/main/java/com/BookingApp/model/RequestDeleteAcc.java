package com.BookingApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class RequestDeleteAcc {
	
	@Id
	@SequenceGenerator(name = "requestSeqGen", sequenceName = "requestSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "requestSeqGen")
	public long id;
	@Column//(name="app_user_id")
	public long appUserId;
	@Column
	public boolean isFinished;
	@Column
	public String text;
	
	
	
	public RequestDeleteAcc() {
		super();
	}


	public RequestDeleteAcc(long appUserId, boolean isFinished, String text) {
		super();
		this.appUserId = appUserId;
		this.isFinished = isFinished;
		this.text = text;
	}
	
	
	
}
