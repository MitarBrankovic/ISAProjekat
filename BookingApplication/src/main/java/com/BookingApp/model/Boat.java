package com.BookingApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Boat {
	@Id
	@SequenceGenerator(name = "boatSeqGen", sequenceName = "boatSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "boatSeqGen")
	public long id;
	@Column
	public String name;
	@Column
	public String boatType;
	@Column
	public double length;
	@Column
	public String engineNumber;
	@Column
	public long enginePower;
	@Column
	public double maxSpeed;
	@Column
	public String navigationEquipment;
	@Column
	public String address;
	@Column
	public String description;
	//public String images ?
	@Column
	public long capacity;
	//public termini termini ?
	@Column
	public String rules;
	@Column
	public String fishingEquipment;
	@Column
	public String priceList;
	@Column
	public double pricePerHour;
	@Enumerated(value = EnumType.STRING)
	public CancellationTerms cancellationTerms;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.EAGER)
	public ShipOwner shipOwner;
	@Column
	public double rating;
	@Column
	public int maxAmountOfPeople;
	
	public Boat() {
		super();
	}

	public Boat(long id, String name, String boatType, double length, String engineNumber, long enginePower,
			double maxSpeed, String navigationEquipment, String address, String description, long capacity,
			String rules, String fishingEquipment, String priceList, double pricePerHour, CancellationTerms cancellationTerms) {
		super();
		this.id = id;
		this.name = name;
		this.boatType = boatType;
		this.length = length;
		this.engineNumber = engineNumber;
		this.enginePower = enginePower;
		this.maxSpeed = maxSpeed;
		this.navigationEquipment = navigationEquipment;
		this.address = address;
		this.description = description;
		this.capacity = capacity;
		this.rules = rules;
		this.fishingEquipment = fishingEquipment;
		this.priceList = priceList;
		this.pricePerHour = pricePerHour;
		this.cancellationTerms = cancellationTerms;
	}
	
	public Boat(long id, String name, String boatType, double length, String engineNumber, long enginePower,
			double maxSpeed, String navigationEquipment, String address, String description, long capacity,
			String rules, String fishingEquipment, String priceList, double pricePerHour, CancellationTerms cancellationTerms, double rating, int maxAmountOfPeople) {
		super();
		this.id = id;
		this.name = name;
		this.boatType = boatType;
		this.length = length;
		this.engineNumber = engineNumber;
		this.enginePower = enginePower;
		this.maxSpeed = maxSpeed;
		this.navigationEquipment = navigationEquipment;
		this.address = address;
		this.description = description;
		this.capacity = capacity;
		this.rules = rules;
		this.fishingEquipment = fishingEquipment;
		this.priceList = priceList;
		this.pricePerHour = pricePerHour;
		this.cancellationTerms = cancellationTerms;
		this.rating = rating;
		this.maxAmountOfPeople = maxAmountOfPeople;
	}
	
}
