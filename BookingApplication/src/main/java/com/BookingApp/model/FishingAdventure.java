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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Type;

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
	@Lob
	@Type(type = "org.hibernate.type.ImageType")
	public byte[] photo;
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
	@ManyToOne(fetch = FetchType.EAGER)
	public FishingInstructor fishingInstructor;
	
	public FishingAdventure() {
		super();
	}
	
	public FishingAdventure(long id, String name, String address, String city, String description,
			byte[] photo, long maxAmountOfPeople, String behaviourRules, String equipment, double pricePerHour, double rating,
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
			byte[] photo, long maxAmountOfPeople, String behaviourRules, String equipment, double pricePerHour, double rating,
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
