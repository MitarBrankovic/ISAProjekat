package com.BookingApp.dto;

import java.time.LocalDate;

import com.BookingApp.model.Boat;
import com.BookingApp.model.Client;

public class ReserveBoatDto {

	public LocalDate datePick;
	public int time;
	public int day;
	public Client client;
	public Boat boat;
	public int totalPrice;
	public String additionalPricingText;
	
	public ReserveBoatDto() {}
	
	public ReserveBoatDto(LocalDate datePick, int time, int day, Client client, Boat boat,
			int totalPrice, String additionalPricingText) {
		super();
		this.datePick = datePick;
		this.time = time;
		this.day = day;
		this.client = client;
		this.boat = boat;
		this.totalPrice = totalPrice;
		this.additionalPricingText = additionalPricingText;
	}
}
