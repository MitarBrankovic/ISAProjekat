package com.BookingApp.dto;

import com.BookingApp.model.AppUser;

public class ComplaintDto {

	public long id;
	public String text;
	public String entity;
	public String nameSurnameOwner;
	public AppUser client;
	public long entityId;
	public long ownerId;
	
	public ComplaintDto() {}

	public ComplaintDto(long id, String text, String entity, String nameSurnameOwner, AppUser client, long entityId,
			long ownerId) {
		super();
		this.id = id;
		this.text = text;
		this.entity = entity;
		this.nameSurnameOwner = nameSurnameOwner;
		this.client = client;
		this.entityId = entityId;
		this.ownerId = ownerId;
	}
	

	
	
}
