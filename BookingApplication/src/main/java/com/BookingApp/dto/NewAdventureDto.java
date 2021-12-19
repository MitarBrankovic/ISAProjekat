package com.BookingApp.dto;

public class NewAdventureDto {
	
	public String name;
	public String address;
	public String city;
	public String description;
	public String photo;
	public long maxAmountOfPeople;
	public String behaviourRules;
	public String equipment;
	public double pricePerHour;
	public long cancellingPrecentage;
	public long instructorsId;
	
	public NewAdventureDto(String name, String address, String city, String description, String photo,
			long maxAmountOfPeople, String behaviourRules, String equipment, double pricePerHour,
			long cancellingPrecentage) {
		super();
		this.name = name;
		this.address = address;
		this.city = city;
		this.description = description;
		this.photo = photo;
		this.maxAmountOfPeople = maxAmountOfPeople;
		this.behaviourRules = behaviourRules;
		this.equipment = equipment;
		this.pricePerHour = pricePerHour;
		this.cancellingPrecentage = cancellingPrecentage;
	}
	
	public NewAdventureDto() {
		super();
	}
	
	
}
