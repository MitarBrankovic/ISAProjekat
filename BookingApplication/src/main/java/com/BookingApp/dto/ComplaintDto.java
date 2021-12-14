package com.BookingApp.dto;


import com.BookingApp.model.AppUser;
import com.BookingApp.model.Cottage;

public class ComplaintDto {

	public String text;
	public Cottage cottage;
	public AppUser appUser;
	
	
	
	public ComplaintDto() {}



	public ComplaintDto(String text, Cottage cottage, AppUser appUser) {
		this.text = text;
		this.cottage = cottage;
		this.appUser = appUser;
	}
	
	
}
