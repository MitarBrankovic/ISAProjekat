package com.BookingApp.dto;

import java.time.LocalDateTime;

public class ChartInfoDto implements Comparable<ChartInfoDto> {

	public LocalDateTime dateAndTime;
	public double price;
	
	public ChartInfoDto(LocalDateTime appointmentStart, double price) {
		super();
		this.dateAndTime = appointmentStart;
		this.price = price;
	}
	
	public ChartInfoDto() {

	}

	@Override
	public int compareTo(ChartInfoDto o) {
		if (dateAndTime == null || o.dateAndTime == null)
		      return 0;
		    return dateAndTime.compareTo(o.dateAndTime);
	}
	
}
