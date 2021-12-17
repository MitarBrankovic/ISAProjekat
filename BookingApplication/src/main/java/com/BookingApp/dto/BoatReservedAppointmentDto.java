package com.BookingApp.dto;

import com.BookingApp.model.BoatAppointment;

public class BoatReservedAppointmentDto {
	public BoatAppointment appointment;
	public boolean dateIsCorrect;
	
	public BoatReservedAppointmentDto() {
		super();
	}

	public BoatReservedAppointmentDto(BoatAppointment appointment, boolean dateIsCorrect) {
		super();
		this.appointment = appointment;
		this.dateIsCorrect = dateIsCorrect;
	}
}
