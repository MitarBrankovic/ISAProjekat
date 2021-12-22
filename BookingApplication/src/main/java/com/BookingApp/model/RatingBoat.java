package com.BookingApp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class RatingBoat {

	@Id
	@SequenceGenerator(name = "myRatingAdvSeqGen", sequenceName = "myRatingAdvSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "myRatingAdvSeqGen")
	public long id;
	@OneToOne
	public Boat boat;
	@Column
	public double rating;
	@Column
	public LocalDateTime date;
	@OneToOne
	public AppUser client;
	@Column
	public boolean isApproved;
	@Column 
	public String revision;
	
	public RatingBoat() {}


	public RatingBoat(long id, Boat boat, double rating, LocalDateTime date, AppUser client, boolean isApproved, String revision) {
		super();
		this.id = id;
		this.boat = boat;
		this.rating = rating;
		this.date = date;
		this.client = client;
		this.isApproved = isApproved;
		this.revision = revision;
	}
	
	public RatingBoat(Boat boat, double rating, LocalDateTime date, AppUser client, boolean isApproved, String revision) {
		super();
		this.boat = boat;
		this.rating = rating;
		this.date = date;
		this.client = client;
		this.isApproved = isApproved;
		this.revision = revision;
	}
}
