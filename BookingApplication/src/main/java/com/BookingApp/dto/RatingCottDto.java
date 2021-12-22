package com.BookingApp.dto;

import com.BookingApp.model.AppUser;
import com.BookingApp.model.Cottage;

public class RatingCottDto {

	public Cottage cottage;
	public AppUser client;
	public double rating;
	public String revision;
	
	public RatingCottDto() {}

	public RatingCottDto(Cottage cottage, AppUser client, double rating, String revision) {
		super();
		this.cottage = cottage;
		this.client = client;
		this.rating = rating;
		this.revision = revision;
	}
}


