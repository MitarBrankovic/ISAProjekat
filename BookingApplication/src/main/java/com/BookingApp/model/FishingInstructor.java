package com.BookingApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class FishingInstructor extends AppUser  {
	@Column
	public String text;

	public FishingInstructor(long id, String name, String surname, String email, String password, String address, String city,
			String country, String phoneNumber, UserType role, String verificationCode, Boolean firstLogin, String text) {
		super(id, name, surname, email, password, address, city, country, phoneNumber,role, verificationCode, firstLogin);
		this.text = text;
	}
	
	
	public FishingInstructor(AppUser appUser, String text) {
		super(appUser.id, appUser.name, appUser.surname, appUser.email, appUser.password, appUser.address, appUser.city, appUser.country, appUser.phoneNumber, appUser.role, appUser.verificationCode, appUser.firstLogin);
		this.text = text;
	}
}
