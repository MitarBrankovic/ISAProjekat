package com.BookingApp.dto;

import com.BookingApp.model.FishingAppointment;

public class FishingAppointmentReservedDto {
	
	public FishingAppointment appointment;
	public boolean dateIsCorrect;
	
	public FishingAppointmentReservedDto() {
		super();
	}

	public FishingAppointmentReservedDto(FishingAppointment appointment, boolean dateIsCorrect) {
		super();
		this.appointment = appointment;
		this.dateIsCorrect = dateIsCorrect;
	}
}
