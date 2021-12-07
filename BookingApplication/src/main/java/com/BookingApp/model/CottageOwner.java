package com.BookingApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class CottageOwner extends AppUser {
	@Column
	public String cottageText;

	public CottageOwner(long id, String name, String surname, String email, String password, String address,
			String city, String country, UserType role, String verificationCode, Boolean firstLogin,
			String cottageText) {
		super(id, name, surname, email, password, address, city, country, role, verificationCode, firstLogin);
		this.cottageText = cottageText;
	}




	
	
}
