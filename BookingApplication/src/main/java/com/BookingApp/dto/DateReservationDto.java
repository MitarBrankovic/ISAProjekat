package com.BookingApp.dto;

import java.time.LocalDate;

public class DateReservationDto {
	
	public LocalDate datePick;
	public int time;
	public int day;
	public int num;
	
	public DateReservationDto() {}
	
	public DateReservationDto(LocalDate datePick, int time, int days, int numOfPeople) {
		super();
		this.datePick = datePick;
		this.time = time;
		this.day = days;
		this.num = numOfPeople;
	}
	
	
}
