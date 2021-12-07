package com.BookingApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Client extends AppUser {
	@Column
	public String text;

	public Client(long id, String name, String surname, String email, String password, String address, String city,
			String country, UserType role, String verificationCode, Boolean firstLogin, String text) {
		super(id, name, surname, email, password, address, city, country, role, verificationCode, firstLogin);
		this.text = text;
	}
	
	
}
