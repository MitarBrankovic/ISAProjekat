package com.BookingApp.model;

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
	public double pricePerHour;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.EAGER)
	public CottageOwner cottageOwner;
	@Column
	public double rating;
	
	public Cottage() {}

	
	public Cottage(long id, String name, String address, String description, int roomsNum, int bedsNum, String rules, String priceList, double pricePerHour) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.roomsNum = roomsNum;
		this.bedsNum = bedsNum;
		this.rules = rules;
		this.priceList = priceList;
		this.pricePerHour = pricePerHour;
	}
	
	public Cottage(long id, String name, String address, String description, int roomsNum, int bedsNum, String rules, String priceList, double pricePerHour, double rating) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.roomsNum = roomsNum;
		this.bedsNum = bedsNum;
		this.rules = rules;
		this.priceList = priceList;
		this.pricePerHour = pricePerHour;
		this.rating = rating;
	}
	
	
}
