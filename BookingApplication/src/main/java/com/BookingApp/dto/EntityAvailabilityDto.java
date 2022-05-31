package com.BookingApp.dto;

import java.time.LocalDateTime;

public class EntityAvailabilityDto {

	public String dateFrom;
	public String timeFrom;
	public String dateUntil;
	public String timeUntil;
	public long entityId;
	public long ownerId;
	
	public EntityAvailabilityDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EntityAvailabilityDto(String dateFrom, String timeFrom, String dateUntil, String timeUntil,
			long entityId, long ownerId) {
		super();
		this.dateFrom = dateFrom;
		this.timeFrom = timeFrom;
		this.dateUntil = dateUntil;
		this.timeUntil = timeUntil;
		this.entityId = entityId;
		this.ownerId = ownerId;
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
	
	
}

