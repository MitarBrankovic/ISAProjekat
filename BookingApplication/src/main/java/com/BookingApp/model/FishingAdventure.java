package com.BookingApp.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
public class FishingAdventure {
	@Id
	@SequenceGenerator(name = "fishingadventureSeqGen", sequenceName = "fishingadventureSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "fishingadventureSeqGen")
	public long id;
	@Column
	public String name;
	@Column
	public String address;
	@Column
	public double longitude; //geo duzina
	@Column
	public double latitude; //geo sirina
	@Column
	public String city;
	@Column
	public String description;
	@Column(length = 10000000)
	public String photo;
	@Column
	public long maxAmountOfPeople;
	@Column
	public String behaviourRules;
	@Column
	public String equipment;
	@Column
	public double pricePerHour;
	@Column
	public double rating;
	@Column
	public long cancellingPrecentage;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.EAGER)
	public FishingInstructor fishingInstructor;
	
	@Version
    public Long version;
	
	public FishingAdventure() {
		super();
	}
	
	public FishingAdventure(long id, String name, String address, String city, String description,
			String photo, long maxAmountOfPeople, String behaviourRules, String equipment, double pricePerHour, double rating,
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
		this.pricePerHour = pricePerHour;
		this.rating = rating;
		this.cancellingPrecentage = cancellingPrecentage;
	}
	
	public FishingAdventure(String name, String address, String city, String description,
			String photo, long maxAmountOfPeople, String behaviourRules, String equipment, double pricePerHour, double rating,
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
		this.pricePerHour = pricePerHour;
		this.rating = rating;
		this.cancellingPrecentage = cancellingPrecentage;
	}
	
	
	
}
