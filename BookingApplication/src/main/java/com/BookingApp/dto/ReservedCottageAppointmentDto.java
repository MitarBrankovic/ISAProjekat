package com.BookingApp.dto;

import java.time.LocalDateTime;

import com.BookingApp.model.CottageAppointment;

public class ReservedCottageAppointmentDto {

	public CottageAppointment appointment;
	public boolean dateIsCorrect;
	public LocalDateTime end;
	
	public ReservedCottageAppointmentDto() {
		super();
	}

	public ReservedCottageAppointmentDto(CottageAppointment appointment, boolean dateIsCorrect, LocalDateTime end) {
		super();
		this.appointment = appointment;
		this.dateIsCorrect = dateIsCorrect;
		this.end = end;
	}
}
