package com.BookingApp.dto;

import com.BookingApp.model.Complaint;

public class AnswerComplaintDto {

	public Complaint complaint;
	public String text;
	
	public AnswerComplaintDto() {}

	public AnswerComplaintDto(Complaint complaint, String text) {
		super();
		this.complaint = complaint;
		this.text = text;
	}
	
}
