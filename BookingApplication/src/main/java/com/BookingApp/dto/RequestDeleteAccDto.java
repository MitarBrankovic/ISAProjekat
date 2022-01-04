package com.BookingApp.dto;

import com.BookingApp.model.UserType;

public class RequestDeleteAccDto {
	

	public long id;
	public long appUserId;
	public boolean isFinished;
	public String text;
	public String appUserName;
	public String appUserType;
	
	
	
	public RequestDeleteAccDto() {
		super();
	}


	public RequestDeleteAccDto(long id, long appUserId, boolean isFinished, String text, String appUserName, String appUserType) {
		super();
		this.id = id;
		this.appUserId = appUserId;
		this.isFinished = isFinished;
		this.text = text;
		this.appUserName = appUserName;
		this.appUserType = appUserType;
	}
	
}
