package com.BookingApp.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RatingBoat {

	@Id
	@SequenceGenerator(name = "myRatingAdvSeqGen", sequenceName = "myRatingAdvSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "myRatingAdvSeqGen")
	public long id;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	public Boat boat;
	@Column
	public double rating;
	@Column
	public LocalDate ratingDate;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	public AppUser client;
	
	public RatingBoat() {}


	public RatingBoat(long id, Boat boat, double rating, LocalDate ratingDate, AppUser client) {
		super();
		this.id = id;
		this.boat = boat;
		this.rating = rating;
		this.ratingDate = ratingDate;
		this.client = client;
	}
	
	public RatingBoat(Boat boat, double rating, LocalDate ratingDate, AppUser client) {
		super();
		this.boat = boat;
		this.rating = rating;
		this.ratingDate = ratingDate;
		this.client = client;
	}
}
