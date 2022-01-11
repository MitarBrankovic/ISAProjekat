package com.BookingApp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RatingFishingAdventure {

	@Id
	@SequenceGenerator(name = "myRatingAdvSeqGen", sequenceName = "myRatingAdvSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "myRatingAdvSeqGen")
	public long id;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	public FishingAdventure fishingAdventure;
	@Column
	public double rating;
	@Column
	public LocalDateTime date;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	public AppUser client;
	@Column
	public boolean isApproved;
	@Column 
	public String revision;
	
	
	public RatingFishingAdventure() {}


	public RatingFishingAdventure(long id, FishingAdventure fishingAdventure, double rating, LocalDateTime ratingDate, AppUser client, boolean isApproved, String revision) {
		super();
		this.id = id;
		this.fishingAdventure = fishingAdventure;
		this.rating = rating;
		this.date = ratingDate;
		this.client = client;
		this.isApproved = isApproved;
		this.revision = revision;
	}
	
	public RatingFishingAdventure(FishingAdventure fishingAdventure, double rating, LocalDateTime ratingDate, AppUser client, boolean isApproved, String revision) {
		super();
		this.fishingAdventure = fishingAdventure;
		this.rating = rating;
		this.date = ratingDate;
		this.client = client;
		this.isApproved = isApproved;
		this.revision = revision;
	}
	
	
	
	
}
