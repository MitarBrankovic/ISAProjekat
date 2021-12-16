package com.BookingApp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class FishingAdventure {
	@Id
	@SequenceGenerator(name = "fishingAdventureSeqGen", sequenceName = "fishingAdventureSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "fishingAdventureSeqGen")
	public long id;
	@Column
	public String name;
	@Column
	public String address;
	@Column
	public String city;
	@Column
	public String description;
	@Column
	public String photo;
	@Column
	public int maxAmountOfPeople;
	@Column
	public String behaviourRules;
	@Column
	public String equipment;
	@Column
	public String priceAndInfo;
	@Column
	public long rating;
	@Column
	public long cancellingPrecentage;
	@ManyToOne(fetch = FetchType.EAGER)
	public FishingInstructor fishingInstructor;
	@JsonIgnore
	@OneToMany
	(mappedBy = "fishingAdventure", fetch = FetchType.EAGER, cascade= CascadeType.ALL)
	public Set<FishingAppointment> fishingAppointments = new HashSet<FishingAppointment>();
	
	public FishingAdventure() {}
	
	public FishingAdventure(long id, String name, String address, String city, String description,
			String photo, int maxAmountOfPeople, String behaviourRules, String equipment, String priceAndInfo, long rating,
			long cancellingPrecentage) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.city = city;
		this.description = description;
		this.photo = photo;
		this.maxAmountOfPeople = maxAmountOfPeople;
		this.behaviourRules = behaviourRules;
		this.equipment = equipment;
		this.priceAndInfo = priceAndInfo;
		this.rating = rating;
		this.cancellingPrecentage = cancellingPrecentage;
	}
	
	public FishingAdventure(String name, String address, String city, String description,
			String photo, int maxAmountOfPeople, String behaviourRules, String equipment, String priceAndInfo, long rating,
			long cancellingPrecentage) {
		super();
		this.name = name;
		this.address = address;
		this.city = city;
		this.description = description;
		this.photo = photo;
		this.maxAmountOfPeople = maxAmountOfPeople;
		this.behaviourRules = behaviourRules;
		this.equipment = equipment;
		this.priceAndInfo = priceAndInfo;
		this.rating = rating;
		this.cancellingPrecentage = cancellingPrecentage;
	}
	
	
	
}
