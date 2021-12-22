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
public class RatingCottage {
	@Id
	@SequenceGenerator(name = "myRatingCottSeqGen", sequenceName = "myRatingCottSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "myRatingCottSeqGen")
	public long id;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	public Cottage cottage;
	@Column
	public double rating;
	@Column
	public LocalDate ratingDate;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	public AppUser client;
	
	public RatingCottage() {}

	public RatingCottage(long id, Cottage cottage, double rating, LocalDate ratingDate, AppUser client) {
		super();
		this.id = id;
		this.cottage = cottage;
		this.rating = rating;
		this.ratingDate = ratingDate;
		this.client = client;
	}
	
	public RatingCottage(Cottage cottage, double rating, LocalDate ratingDate, AppUser client) {
		super();
		this.cottage = cottage;
		this.rating = rating;
		this.ratingDate = ratingDate;
		this.client = client;
	}
	
	
}
