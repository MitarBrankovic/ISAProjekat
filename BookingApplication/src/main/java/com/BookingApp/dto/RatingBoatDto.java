package com.BookingApp.dto;

import com.BookingApp.model.AppUser;
import com.BookingApp.model.Boat;

public class RatingBoatDto {
	
	public Boat boat;
	public AppUser client;
	public double rating;
	public String revision;
	
	public RatingBoatDto() {}

	public RatingBoatDto(Boat boat, AppUser client, double rating, String revision) {
		super();
		this.boat = boat;
		this.client = client;
		this.rating = rating;
		this.revision = revision;
	}
}
