package com.BookingApp.dto;

public class EditBoatDto {
	public long id;
	public String name;
	public String boatType;
	public double length;
	public String engineNumber;
	public long enginePower;
	public double maxSpeed;
	public String navigationEquipment;
	public String address;
	public String description;
	public long capacity;
	public String rules;
	public String fishingEquipment;
	public String priceList;
	public double pricePerHour;
	public int maxAmountOfPeople;
	public long shipOwnerId;
	
	public EditBoatDto(long id, String name, String boatType, double length, String engineNumber, long enginePower, double maxSpeed,
			String navigationEquipment, String address, String description, long capacity, String rules,
			String fishingEquipment, String priceList, double pricePerHour, int maxAmountOfPeople,long shipOwnerId) {
		super();
		this.id = id;
		this.name = name;
		this.boatType = boatType;
		this.length = length;
		this.engineNumber = engineNumber;
		this.enginePower = enginePower;
		this.maxSpeed = maxSpeed;
		this.navigationEquipment = navigationEquipment;
		this.address = address;
		this.description = description;
		this.capacity = capacity;
		this.rules = rules;
		this.fishingEquipment = fishingEquipment;
		this.priceList = priceList;
		this.pricePerHour = pricePerHour;
		this.maxAmountOfPeople = maxAmountOfPeople;
		this.shipOwnerId = shipOwnerId;
	}

	public EditBoatDto() {
		super();
	}
	
	
}
