package com.BookingApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class LoyaltyProgram {

	@Id
	@SequenceGenerator(name = "loyaltySeqGen", sequenceName = "loyaltySeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "loyaltySeqGen")
	public long id;
	@Column
	public double bronzePoints;
	@Column
	public double silverPoints;
	@Column
	public double goldPoints;
	@Column
	public double bronzeClient;
	@Column
	public double silverClient;
	@Column
	public double goldClient;
	@Column
	public double bronzePrecentage;
	@Column
	public double silverPrecentage;
	@Column
	public double goldPrecentage;
	@Column
	public double bronzeDiscount;
	@Column
	public double silverDiscount;
	@Column
	public double goldDiscount;
	
	public LoyaltyProgram(long id, double bronzePoints, double silverPoints, double goldPoints, double bronzeClient,
			double silverClient, double goldClient, double bronzePrecentage, double silverPrecentage, double goldPrecentage,
			double bronzeDiscount, double silverDiscount, double goldDiscount) {
		super();
		this.id = id;
		this.bronzePoints = bronzePoints;
		this.silverPoints = silverPoints;
		this.goldPoints = goldPoints;
		this.bronzeClient = bronzeClient;
		this.silverClient = silverClient;
		this.goldClient = goldClient;
		this.bronzePrecentage = bronzePrecentage;
		this.silverPrecentage = silverPrecentage;
		this.goldPrecentage = goldPrecentage;
		this.bronzeDiscount = bronzeDiscount;
		this.silverDiscount = silverDiscount;
		this.goldDiscount = goldDiscount;
	}
	
	public LoyaltyProgram(double bronzePoints, double silverPoints, double goldPoints, double bronzeClient,
			double silverClient, double goldClient, double bronzePrecentage, double silverPrecentage, double goldPrecentage,
			double bronzeDiscount, double silverDiscount, double goldDiscount) {
		super();
		this.bronzePoints = bronzePoints;
		this.silverPoints = silverPoints;
		this.goldPoints = goldPoints;
		this.bronzeClient = bronzeClient;
		this.silverClient = silverClient;
		this.goldClient = goldClient;
		this.bronzePrecentage = bronzePrecentage;
		this.silverPrecentage = silverPrecentage;
		this.goldPrecentage = goldPrecentage;
		this.bronzeDiscount = bronzeDiscount;
		this.silverDiscount = silverDiscount;
		this.goldDiscount = goldDiscount;
	}

	public LoyaltyProgram() {
		super();
	}
	
	
}
