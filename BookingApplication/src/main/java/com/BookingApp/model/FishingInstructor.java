package com.BookingApp.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class FishingInstructor extends AppUser  {
	@Column
	public String text;
	@Column
	public String biography;
	@Column
	public LocalDateTime availableFrom;
	@Column
	public LocalDateTime availableUntil;
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany
	(mappedBy = "fishingInstructor", fetch = FetchType.EAGER, cascade= CascadeType.ALL)
	public Set<FishingAdventure> fishingAdventures = new HashSet<FishingAdventure>();

	public FishingInstructor() {
		super();
	}
	
	public FishingInstructor(long id, String name, String surname, String email, String password, String address, String city,
			String country, String phoneNumber, UserType role, String verificationCode, Boolean firstLogin, String text, String biography, LocalDateTime from, LocalDateTime until) {
		super(id, name, surname, email, password, address, city, country, phoneNumber,role, verificationCode, firstLogin);
		this.text = text;
		this.biography = biography;
		this.availableFrom = from;
		this.availableUntil = until;
	}
	
	
	public FishingInstructor(AppUser appUser, String text, String biography, LocalDateTime from, LocalDateTime until) {
		super(appUser.id, appUser.name, appUser.surname, appUser.email, appUser.password, appUser.address, appUser.city, appUser.country, appUser.phoneNumber, appUser.role, appUser.verificationCode, appUser.verified);
		this.text = text;
		this.biography = biography;
		this.availableFrom = from;
		this.availableUntil = until;
	}
}
