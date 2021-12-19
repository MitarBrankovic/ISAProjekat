package com.BookingApp.dto;

import com.BookingApp.model.BoatAppointment;

public class ReservedBoatAppointmentDto {
	public BoatAppointment appointment;
	public boolean dateIsCorrect;
	
	public ReservedBoatAppointmentDto() {
		super();
	}

	public ReservedBoatAppointmentDto(BoatAppointment appointment, boolean dateIsCorrect) {
		super();
		this.appointment = appointment;
		this.dateIsCorrect = dateIsCorrect;
	}
}
