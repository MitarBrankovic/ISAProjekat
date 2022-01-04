package com.BookingApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Admin extends AppUser {

	@Column
	public String text;
	@Enumerated(value = EnumType.STRING)
	@Column
	public AdminType adminType;
	
	
	public Admin() {
		super();
	}



	public Admin(long id, String name, String surname, String email, String password, String address, String city,
			String country, String phoneNumber, UserType role, String verificationCode, Boolean firstLogin, String text, AdminType adminType) {
		super(id, name, surname, email, password, address, city, country, phoneNumber,role, verificationCode, firstLogin);
		this.text = text;
		this.adminType = adminType;
	}
	
	public Admin(String name, String surname, String email, String password, String address, String city,
			String country, String phoneNumber, UserType role, String verificationCode, Boolean firstLogin, String text, AdminType adminType) {
		super(name, surname, email, password, address, city, country, phoneNumber,role, verificationCode, firstLogin);
		this.text = text;
		this.adminType = adminType;
	}
	
	public Admin(AppUser appUser, String text, AdminType adminType) {
		super(appUser.id, appUser.name, appUser.surname, appUser.email, appUser.password, appUser.address, appUser.city, appUser.country, appUser.phoneNumber, appUser.role, appUser.verificationCode, appUser.verified);
		this.text = text;
		this.adminType = adminType;
	}
	
}
