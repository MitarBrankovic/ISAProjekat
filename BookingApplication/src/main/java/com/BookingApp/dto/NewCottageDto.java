package com.BookingApp.dto;


public class NewCottageDto {
	public String name;
	public String address;
	public String description;
	public int roomsNum;
	public int bedsNum;
	public String rules;
	public String priceList;
	public double pricePerHour;
	public Long cottageOwnerId;
	public int maxAmountOfPeople;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getRoomsNum() {
		return roomsNum;
	}
	public void setRoomsNum(int roomsNum) {
		this.roomsNum = roomsNum;
	}
	public int getBedsNum() {
		return bedsNum;
	}
	public void setBedsNum(int bedsNum) {
		this.bedsNum = bedsNum;
	}
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}
	public String getPriceList() {
		return priceList;
	}
	public void setPriceList(String priceList) {
		this.priceList = priceList;
	}
	public double getPricePerHour() {
		return pricePerHour;
	}
	public void setPricePerHour(int pricePerHour) {
		this.pricePerHour = pricePerHour;
	}
	public Long getCottageOwnerId() {
		return cottageOwnerId;
	}
	public void setCottageOwnerId(Long cottageOwnerId) {
		this.cottageOwnerId = cottageOwnerId;
	}
	public int getMaxAmountOfPeople() {
		return maxAmountOfPeople;
	}
	public void setMaxAmountOfPeople(int maxAmountOfPeople) {
		this.maxAmountOfPeople = maxAmountOfPeople;
	}
	public NewCottageDto(String name, String address, String description, int roomsNum, int bedsNum, String rules,
			String priceList, double pricePerHour, Long cottageOwnerId, int maxAmountOfPeople) {
		super();
		this.name = name;
		this.address = address;
		this.description = description;
		this.roomsNum = roomsNum;
		this.bedsNum = bedsNum;
		this.rules = rules;
		this.priceList = priceList;
		this.pricePerHour = pricePerHour;
		this.cottageOwnerId = cottageOwnerId;
		this.maxAmountOfPeople = maxAmountOfPeople;
	}
	public NewCottageDto() {}
	
}
