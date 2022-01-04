package com.BookingApp.dto;

public class AddAdminDto {
	
	public String name;
	public String surname;
	public String email;
	public String password;
	public String address;
	public String city; 
	public String country;
	public String phoneNumber;
	
	public AddAdminDto(String name, String surname, String email, String password, String address, String city,
			String country, String phoneNumber) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phoneNumber = phoneNumber;
	}

	public AddAdminDto() {
		super();
	}
	
	
}
