package com.BookingApp.dto;

public class PricelistItemRemoveDto {
	
	public long itemId;
	public long instructorId;

	
	public PricelistItemRemoveDto(long itemId, long instructorId) {
		super();
		this.itemId = itemId;
		this.instructorId = instructorId;
	}


	public PricelistItemRemoveDto() {}
}
