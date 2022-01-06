package com.BookingApp.dto;

import java.time.LocalDateTime;

import com.BookingApp.model.BoatAppointment;

public class ReservedBoatAppointmentDto {
	public BoatAppointment appointment;
	public boolean dateIsCorrect;
	public LocalDateTime end;
	
	public ReservedBoatAppointmentDto() {
		super();
	}

	public ReservedBoatAppointmentDto(BoatAppointment appointment, boolean dateIsCorrect, LocalDateTime end) {
		super();
		this.appointment = appointment;
		this.dateIsCorrect = dateIsCorrect;
		this.end = end;
	}
}
