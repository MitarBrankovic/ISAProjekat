package com.BookingApp.dto;

import com.BookingApp.model.Complaint;

public class AnswerComplaintDto {

	public ComplaintDto complaint;
	public String text;
	
	public AnswerComplaintDto() {}

	public AnswerComplaintDto(ComplaintDto complaint, String text) {
		super();
		this.complaint = complaint;
		this.text = text;
	}
	
}
