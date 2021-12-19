package com.BookingApp.dto;

public class SearchInstructorsAdventuresDto {

	public boolean nameAsc;
    public boolean nameDesc;
    public boolean addressAsc;
    public boolean addressDesc;
    public String name;
    public String address;
    public long instructorsId;
    
	public SearchInstructorsAdventuresDto(boolean nameAsc, boolean nameDesc, boolean addressAsc, boolean addressDesc,
			String name, String address, long instructorsId) {
		super();
		this.nameAsc = nameAsc;
		this.nameDesc = nameDesc;
		this.addressAsc = addressAsc;
		this.addressDesc = addressDesc;
		this.name = name;
		this.address = address;
		this.instructorsId = instructorsId;
	}
    
	public SearchInstructorsAdventuresDto() {}
}
