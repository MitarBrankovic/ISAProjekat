package com.BookingApp.dto;

public class SearchAppointmentDto {
	
    public boolean nameAsc;
    public boolean nameDesc;
    public boolean dateAsc;
    public boolean dateDesc;
    public boolean durationAsc;
    public boolean durationDesc;
    public boolean priceAsc;
    public boolean priceDesc;
    public String name;
    public String owner;
    public long activeUserId;
    
    public SearchAppointmentDto() {}

	public SearchAppointmentDto(boolean nameAsc, boolean nameDesc, boolean dateAsc, boolean dateDesc,
			boolean durationAsc, boolean durationDesc, boolean priceAsc, boolean priceDesc, String name, String owner, long userId) {
		super();
		this.nameAsc = nameAsc;
		this.nameDesc = nameDesc;
		this.dateAsc = dateAsc;
		this.dateDesc = dateDesc;
		this.durationAsc = durationAsc;
		this.durationDesc = durationDesc;
		this.priceAsc = priceAsc;
		this.priceDesc = priceDesc;
		this.name = name;
		this.owner = owner;
		this.activeUserId = userId;
	}


}
