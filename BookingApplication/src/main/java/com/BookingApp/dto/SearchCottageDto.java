package com.BookingApp.dto;

public class SearchCottageDto {

    public boolean nameAsc;
    public boolean nameDesc;
    public boolean addressAsc;
    public boolean addressDesc;
    public String name;
    public String address;
    
    public SearchCottageDto() {}

	public SearchCottageDto(boolean nameAsc, boolean nameDesc, boolean addressAsc, boolean addressDesc, String name,
			String address) {
		super();
		this.nameAsc = nameAsc;
		this.nameDesc = nameDesc;
		this.addressAsc = addressAsc;
		this.addressDesc = addressDesc;
		this.name = name;
		this.address = address;
	}
    
    
	
}
