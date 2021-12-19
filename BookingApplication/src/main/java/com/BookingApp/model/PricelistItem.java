package com.BookingApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class PricelistItem {
	
	@Id
	@SequenceGenerator(name = "pricelistSeqGen", sequenceName = "pricelistSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "pricelistSeqGen")
	public long id;
	@Column
	public double price;
	@Column
	public String description;
	@ManyToOne(fetch = FetchType.EAGER)
	public AppUser appUser;
	
	public PricelistItem() {}
	
	public PricelistItem(long id, double price, String description) {
		super();
		this.id = id;
		this.price = price;
		this.description = description;
	}

	public PricelistItem(double price, String description, AppUser appUser) {
		super();
		this.price = price;
		this.description = description;
		this.appUser = appUser;
	}

	public PricelistItem(double price, String description) {
		super();
		this.price = price;
		this.description = description;
	}

}
