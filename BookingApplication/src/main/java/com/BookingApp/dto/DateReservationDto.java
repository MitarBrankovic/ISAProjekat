package com.BookingApp.dto;

import java.time.LocalDate;

public class DateReservationDto {
	
	public LocalDate datePick;
	public int time;
	
	public DateReservationDto() {}
	
	public DateReservationDto(LocalDate datePick, int time) {
		super();
		this.datePick = datePick;
		this.time = time;
	}
	
	
}
