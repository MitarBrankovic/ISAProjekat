package com.BookingApp.dto;

public class SearchOwnerDto {

    public boolean nameAsc;
    public boolean nameDesc;
    public boolean addressAsc;
    public boolean addressDesc;
    public boolean rateAsc;
    public boolean rateDesc;
    public String name;
    public String address;
    public Long ownerId;
    
    public SearchOwnerDto() {}

	public SearchOwnerDto(boolean nameAsc, boolean nameDesc, boolean addressAsc, boolean addressDesc,  boolean rateAsc, boolean rateDesc, String name,
			String address, Long ownerId) {
		super();
		this.nameAsc = nameAsc;
		this.nameDesc = nameDesc;
		this.addressAsc = addressAsc;
		this.addressDesc = addressDesc;
		this.rateAsc = rateAsc;
		this.rateDesc = rateDesc;
		this.name = name;
		this.address = address;
		this.ownerId = ownerId;
	}
    
    
	
}
