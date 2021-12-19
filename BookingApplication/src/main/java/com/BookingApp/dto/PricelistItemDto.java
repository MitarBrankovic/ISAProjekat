package com.BookingApp.dto;

public class PricelistItemDto {

	public long instructorsId;
	public String description;
	public double price;
	
	public PricelistItemDto(long instructorsId, String description, double price) {
		super();
		this.instructorsId = instructorsId;
		this.description = description;
		this.price = price;
	}
	
	public PricelistItemDto() {
		super();
	}
	
	
}
