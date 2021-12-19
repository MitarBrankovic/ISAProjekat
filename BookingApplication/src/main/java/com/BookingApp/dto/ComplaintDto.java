package com.BookingApp.dto;


import com.BookingApp.model.AppUser;

public class ComplaintDto {

	public String text;
	public long entityId;
	public long owner;
	public AppUser client;
	
	
	public ComplaintDto() {}

	public ComplaintDto(String text, long entityId, long owner, AppUser client) {
		this.text = text;
		this.entityId = entityId;
		this.owner = owner;
		this.client = client;
	}
	
	
}
