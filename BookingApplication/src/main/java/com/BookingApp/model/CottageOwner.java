package com.BookingApp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CottageOwner extends AppUser {
	@Column
	public String cottageText;
	@JsonIgnore
	@OneToMany
	(mappedBy = "cottageOwner", fetch = FetchType.EAGER, cascade= CascadeType.ALL)
	public Set<Cottage> cottages = new HashSet<Cottage>();

	public CottageOwner() {
		super();
	}
	
	public CottageOwner(long id, String name, String surname, String email, String password, String address,
			String city, String country, String phoneNumber, UserType role, String verificationCode, Boolean firstLogin,
			String cottageText) {
		super(id, name, surname, email, password, address, city, country, phoneNumber, role, verificationCode, firstLogin);
		this.cottageText = cottageText;
	}
	
	public CottageOwner(AppUser appUser, String text) {
		super(appUser.id, appUser.name, appUser.surname, appUser.email, appUser.password, appUser.address, appUser.city, appUser.country, appUser.phoneNumber, appUser.role, appUser.verificationCode, appUser.verified);
		this.cottageText = text;
	}
	
	
}
