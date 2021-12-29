package com.BookingApp.dto;

public class SearchDto {

    public boolean nameAsc;
    public boolean nameDesc;
    public boolean addressAsc;
    public boolean addressDesc;
    public boolean rateAsc;
    public boolean rateDesc;
    public String name;
    public String address;
    
    public SearchDto() {}

	public SearchDto(boolean nameAsc, boolean nameDesc, boolean addressAsc, boolean addressDesc,  boolean rateAsc, boolean rateDesc, String name,
			String address) {
		super();
		this.nameAsc = nameAsc;
		this.nameDesc = nameDesc;
		this.addressAsc = addressAsc;
		this.addressDesc = addressDesc;
		this.rateAsc = rateAsc;
		this.rateDesc = rateDesc;
		this.name = name;
		this.address = address;
	}
    
    
	
}
