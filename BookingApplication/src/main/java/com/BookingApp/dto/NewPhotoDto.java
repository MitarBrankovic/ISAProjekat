package com.BookingApp.dto;

public class NewPhotoDto {
	public String photo;
	public long entityId;
	
	public NewPhotoDto(String photo, long entityId) {
		super();
		this.photo = photo;
		this.entityId = entityId;
	}
	
	public NewPhotoDto() {
		super();
	}
	
	

}
