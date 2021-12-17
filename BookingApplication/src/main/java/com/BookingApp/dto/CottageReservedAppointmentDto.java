package com.BookingApp.dto;

import com.BookingApp.model.CottageAppointment;

public class CottageReservedAppointmentDto {

	public CottageAppointment appointment;
	public boolean dateIsCorrect;
	
	public CottageReservedAppointmentDto() {
		super();
	}

	public CottageReservedAppointmentDto(CottageAppointment appointment, boolean dateIsCorrect) {
		super();
		this.appointment = appointment;
		this.dateIsCorrect = dateIsCorrect;
	}
}
