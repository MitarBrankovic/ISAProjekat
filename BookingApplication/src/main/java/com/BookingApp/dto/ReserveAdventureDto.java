package com.BookingApp.dto;

import java.time.LocalDate;

import com.BookingApp.model.Client;
import com.BookingApp.model.FishingAdventure;

public class ReserveAdventureDto {

	public LocalDate datePick;
	public int time;
	public Client client;
	public FishingAdventure fishingAdventure;
	public int totalPrice;
	public String additionalPricingText;
	
	public ReserveAdventureDto() {}
	
	public ReserveAdventureDto(LocalDate datePick, int time, Client client, FishingAdventure fishingAdventure,
			int totalPrice, String additionalPricingText) {
		super();
		this.datePick = datePick;
		this.time = time;
		this.client = client;
		this.fishingAdventure = fishingAdventure;
		this.totalPrice = totalPrice;
		this.additionalPricingText = additionalPricingText;
	}
	
	
	
}
