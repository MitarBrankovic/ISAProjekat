package com.BookingApp.dto;

import java.time.LocalDateTime;

import com.BookingApp.model.FishingAppointment;

public class ReservedFishingAppointmentDto {
	
	public FishingAppointment appointment;
	public boolean dateIsCorrect;
	public LocalDateTime end;
	
	public ReservedFishingAppointmentDto() {
		super();
	}

	public ReservedFishingAppointmentDto(FishingAppointment appointment, boolean dateIsCorrect, LocalDateTime end) {
		super();
		this.appointment = appointment;
		this.dateIsCorrect = dateIsCorrect;
		this.end = end;
	}
}
