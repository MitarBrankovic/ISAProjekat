package com.BookingApp.dto;

import com.BookingApp.model.AppUser;
import com.BookingApp.model.FishingAdventure;

public class RatingAdvDto {

	public FishingAdventure fishingAdventure;
	public AppUser client;
	public double rating;
	public String revision;
	
	
	
	public RatingAdvDto() {}

	public RatingAdvDto(FishingAdventure fishingAdventure, AppUser client, double rating, String revision) {
		super();
		this.fishingAdventure = fishingAdventure;
		this.client = client;
		this.rating = rating;
		this.revision = revision;
	}

	
}
