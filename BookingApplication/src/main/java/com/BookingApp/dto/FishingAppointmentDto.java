package com.BookingApp.dto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class FishingAppointmentDto {
	
	public String dateFrom;
	public String timeFrom;
	public String dateUntil;
	public String timeUntil;
	public String address;
	public String city;
	public int maxAmountOfPeople;
	public String extraNotes;
	public int price;
	public long adventureId;
	
	public FishingAppointmentDto() {}
	
	public FishingAppointmentDto(String dateFrom, String timeFrom, String dateUntil, String timeUntil, String address,
			String city, int maxAmountOfPeople, String extraNotes, int price, long adventureId) {
		super();
		this.dateFrom = dateFrom;
		this.timeFrom = timeFrom;
		this.dateUntil = dateUntil;
		this.timeUntil = timeUntil;
		this.address = address;
		this.city = city;
		this.maxAmountOfPeople = maxAmountOfPeople;
		this.extraNotes = extraNotes;
		this.price = price;
		this.adventureId = adventureId;
	}
	
	public LocalDateTime formatDateFrom() {
		String[] Date = this.dateFrom.split("-");
		String[] Time = this.timeFrom.split(":");
		int year = Integer.parseInt(Date[0]);
		int month = Integer.parseInt(Date[1]);
		int day = Integer.parseInt(Date[2]);
		int hour = Integer.parseInt(Time[0]);
		int minute = Integer.parseInt(Time[1]);
		return LocalDateTime.of(year, month, day, hour, minute);
	}
	
	public LocalDateTime formatDateUntil() {
		String[] Date = this.dateUntil.split("-");
		String[] Time = this.timeUntil.split(":");
		int year = Integer.parseInt(Date[0]);
		int month = Integer.parseInt(Date[1]);
		int day = Integer.parseInt(Date[2]);
		int hour = Integer.parseInt(Time[0]);
		int minute = Integer.parseInt(Time[1]);
		return LocalDateTime.of(year, month, day, hour, minute);
	}
	
	public long durationInHours() {
		return ChronoUnit.HOURS.between(formatDateFrom(), formatDateUntil());
	}
	
}
