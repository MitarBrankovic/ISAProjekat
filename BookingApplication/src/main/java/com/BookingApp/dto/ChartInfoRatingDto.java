package com.BookingApp.dto;

import java.time.LocalDateTime;

public class ChartInfoRatingDto  {

	public ChartInfoRatingDto(String name, double rating) {
		super();
		this.name = name;
		this.rating = rating;
	}


	public String name;
	public double rating;
	
	
	public ChartInfoRatingDto() {

	}

	
	
}
