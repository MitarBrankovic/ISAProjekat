package com.BookingApp.dto;

public class SearchAdventureDto {

	
    public boolean nameAsc;
    public boolean nameDesc;
    public boolean addressAsc;
    public boolean addressDesc;
    public String name;
    public String address;
    public String instructor;
    
    public SearchAdventureDto() {}

	public SearchAdventureDto(boolean nameAsc, boolean nameDesc, boolean addressAsc, boolean addressDesc, String name,
			String address, String instructor) {
		super();
		this.nameAsc = nameAsc;
		this.nameDesc = nameDesc;
		this.addressAsc = addressAsc;
		this.addressDesc = addressDesc;
		this.name = name;
		this.address = address;
		this.instructor = instructor;
	}
}
