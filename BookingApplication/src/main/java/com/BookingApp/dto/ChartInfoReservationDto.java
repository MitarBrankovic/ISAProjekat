package com.BookingApp.dto;

import java.time.LocalDateTime;

public class ChartInfoReservationDto  {

	public String name;
	public int amount;
	
	public ChartInfoReservationDto(String name, int amount) {
		super();
		this.name = name;
		this.amount = amount;
	}
	
	public ChartInfoReservationDto() {

	}


	
}
