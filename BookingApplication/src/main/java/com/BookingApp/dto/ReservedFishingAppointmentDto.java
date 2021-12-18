package com.BookingApp.dto;

import com.BookingApp.model.FishingAppointment;

public class ReservedFishingAppointmentDto {
	
	public FishingAppointment appointment;
	public boolean dateIsCorrect;
	
	public ReservedFishingAppointmentDto() {
		super();
	}

	public ReservedFishingAppointmentDto(FishingAppointment appointment, boolean dateIsCorrect) {
		super();
		this.appointment = appointment;
		this.dateIsCorrect = dateIsCorrect;
	}
}
