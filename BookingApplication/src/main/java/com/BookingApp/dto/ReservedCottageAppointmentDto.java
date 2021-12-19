package com.BookingApp.dto;

import com.BookingApp.model.CottageAppointment;

public class ReservedCottageAppointmentDto {

	public CottageAppointment appointment;
	public boolean dateIsCorrect;
	
	public ReservedCottageAppointmentDto() {
		super();
	}

	public ReservedCottageAppointmentDto(CottageAppointment appointment, boolean dateIsCorrect) {
		super();
		this.appointment = appointment;
		this.dateIsCorrect = dateIsCorrect;
	}
}
