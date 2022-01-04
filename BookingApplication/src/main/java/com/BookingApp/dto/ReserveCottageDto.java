package com.BookingApp.dto;

import java.time.LocalDate;

import com.BookingApp.model.Client;
import com.BookingApp.model.Cottage;

public class ReserveCottageDto {

	public LocalDate datePick;
	public int time;
	public Client client;
	public Cottage cottage;
	public int totalPrice;
	public String additionalPricingText;
	
	public ReserveCottageDto() {}
	
	public ReserveCottageDto(LocalDate datePick, int time, Client client, Cottage cottage,
			int totalPrice, String additionalPricingText) {
		super();
		this.datePick = datePick;
		this.time = time;
		this.client = client;
		this.cottage = cottage;
		this.totalPrice = totalPrice;
		this.additionalPricingText = additionalPricingText;
	}
}
