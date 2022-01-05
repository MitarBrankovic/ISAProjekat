package com.BookingApp.dto;

public class AppointmentReportDto {

	 public String type;
	 public String comment;
	 public long appointmentId;
	 public long ownerId;
	 public long clientId;
	 
	public AppointmentReportDto(String type, String comment, long appointmentId, long ownerId, long clientId) {
		super();
		this.type = type;
		this.comment = comment;
		this.appointmentId = appointmentId;
		this.ownerId = ownerId;
		this.clientId = clientId;
	}

	public AppointmentReportDto() {
		super();
	}
	 
	 
}
