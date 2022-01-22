package com.BookingApp.dto;

import com.BookingApp.model.AppUser;
import com.BookingApp.model.CottageOwner;

public class RatingCottOwnerDto {

	public CottageOwner cottageOwner;
	public AppUser client;
	public double rating;
	public String revision;
	
	public RatingCottOwnerDto() {}

	public RatingCottOwnerDto(CottageOwner cottageOwner, AppUser client, double rating, String revision) {
		super();
		this.cottageOwner = cottageOwner;
		this.client = client;
		this.rating = rating;
		this.revision = revision;
	}
}
