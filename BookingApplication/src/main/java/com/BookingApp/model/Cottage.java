package com.BookingApp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Cottage {
	@Id
	@SequenceGenerator(name = "cottageSeqGen", sequenceName = "cottageSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cottageSeqGen")
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
	public String description;
	//public String images;
	public int roomsNum;
	@Column
	public int bedsNum;
	//public String termini;
	@Column
	public String rules;
	@Column
	public String priceList;
	@Column
	public LocalDateTime availableFrom;
	@Column
	public LocalDateTime availableUntil;
	@Column
	public double pricePerHour;//mislimo na dane
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.EAGER)
	public CottageOwner cottageOwner;
	@Column
	public double rating;
	@Column
	public int maxAmountOfPeople;
	
	public Cottage() {}

	
	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public Cottage(long id, String name, String address, String description, int roomsNum, int bedsNum, String rules, String priceList, double pricePerHour, int maxAmountOfPeople) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.roomsNum = roomsNum;
		this.bedsNum = bedsNum;
		this.availableFrom = LocalDateTime.now();
		this.availableUntil = LocalDateTime.now().plusDays(7);
		this.rules = rules;
		this.priceList = priceList;
		this.pricePerHour = pricePerHour;
		this.maxAmountOfPeople = maxAmountOfPeople;
	}
	public Cottage(String name, String address, String description, int roomsNum, int bedsNum, String rules, String priceList, double pricePerHour, int maxAmountOfPeople) {
		super();
		this.name = name;
		this.address = address;
		this.description = description;
		this.roomsNum = roomsNum;
		this.bedsNum = bedsNum;
		this.availableFrom = LocalDateTime.now();
		this.availableUntil = LocalDateTime.now().plusDays(7);
		this.rules = rules;
		this.priceList = priceList;
		this.pricePerHour = pricePerHour;
		this.maxAmountOfPeople = maxAmountOfPeople;
	}
	
	public Cottage(long id, String name, String address, String description, int roomsNum, int bedsNum, String rules, String priceList, double pricePerHour, double rating, int maxAmountOfPeople) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.roomsNum = roomsNum;
		this.bedsNum = bedsNum;
		this.rules = rules;
		this.priceList = priceList;
		this.availableFrom = LocalDateTime.now();
		this.availableUntil = LocalDateTime.now().plusDays(7);
		this.pricePerHour = pricePerHour;
		this.rating = rating;
		this.maxAmountOfPeople = maxAmountOfPeople;
	}
	
	public Cottage(long id, String name, String address, String description, int roomsNum, int bedsNum, String rules,
			String priceList, LocalDateTime availableFrom, LocalDateTime availableUntil, double pricePerHour, double rating, int maxAmountOfPeople) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.roomsNum = roomsNum;
		this.bedsNum = bedsNum;
		this.rules = rules;
		this.priceList = priceList;
		this.availableFrom = availableFrom;
		this.availableUntil = availableUntil;
		this.pricePerHour = pricePerHour;
		this.rating = rating;
		this.maxAmountOfPeople = maxAmountOfPeople;
	}


	public Cottage(long id, String name, String address, String description, int roomsNum, int bedsNum, String rules,
			String priceList, LocalDateTime availableFrom, LocalDateTime availableUntil, double pricePerHour,
			CottageOwner cottageOwner, double rating, int maxAmountOfPeople) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.roomsNum = roomsNum;
		this.bedsNum = bedsNum;
		this.rules = rules;
		this.priceList = priceList;
		this.availableFrom = availableFrom;
		this.availableUntil = availableUntil;
		this.pricePerHour = pricePerHour;
		this.cottageOwner = cottageOwner;
		this.rating = rating;
		this.maxAmountOfPeople = maxAmountOfPeople;
	}
	
	
}
