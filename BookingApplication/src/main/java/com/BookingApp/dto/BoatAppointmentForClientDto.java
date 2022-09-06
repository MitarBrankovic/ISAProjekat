package com.BookingApp.dto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class BoatAppointmentForClientDto {
	
	public String dateFrom;
	public String timeFrom;
	public String dateUntil;
	public String timeUntil;
	public String extraNotes;
	public double price;
	public long boatId;
	public long clientId;
	
	public BoatAppointmentForClientDto() {}
	
	public BoatAppointmentForClientDto(String dateFrom, String timeFrom, String dateUntil, String timeUntil, String extraNotes, double price, long boatId, long clientId) {
		super();
		this.dateFrom = dateFrom;
		this.timeFrom = timeFrom;
		this.dateUntil = dateUntil;
		this.timeUntil = timeUntil;
		this.extraNotes = extraNotes;
		this.price = price;
		this.boatId = boatId;
		this.clientId = clientId;
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

