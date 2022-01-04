package com.BookingApp.dto;

public class ClientRatingDto {

	public long ratingId;
	public long ownerId;
	public String clientName;
	public String clientEmail;
	public String entityName;
	public String entityAddress;
	public String ownerName;
	public String ownerEmail;
	public double rating;
	public String comment;
	public String ratingDateTime;

	public ClientRatingDto(long ratingId, long ownerId, String clientName, String clientEmail, String entityName,
			String entityAddress, String ownerName, String ownerEmail, double rating, String comment,
			String ratingDateTime) {
		super();
		this.ratingId = ratingId;
		this.ownerId = ownerId;
		this.clientName = clientName;
		this.clientEmail = clientEmail;
		this.entityName = entityName;
		this.entityAddress = entityAddress;
		this.ownerName = ownerName;
		this.ownerEmail = ownerEmail;
		this.rating = rating;
		this.comment = comment;
		this.ratingDateTime = ratingDateTime;
	}



	public ClientRatingDto() {
		super();
	}

	
}
