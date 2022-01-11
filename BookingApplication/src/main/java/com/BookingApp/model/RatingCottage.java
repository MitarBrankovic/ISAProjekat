package com.BookingApp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
public class RatingCottage {
	@Id
	@SequenceGenerator(name = "myRatingCottSeqGen", sequenceName = "myRatingCottSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "myRatingCottSeqGen")
	public long id;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	public Cottage cottage;
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
	
	public RatingCottage() {}

	public RatingCottage(long id, Cottage cottage, double rating, LocalDateTime date, AppUser client, boolean isApproved, String revision) {
		super();
		this.id = id;
		this.cottage = cottage;
		this.rating = rating;
		this.date = date;
		this.client = client;
		this.isApproved = isApproved;
		this.revision = revision;
	}
	
	public RatingCottage(Cottage cottage, double rating, LocalDateTime date, AppUser client, boolean isApproved, String revision) {
		super();
		this.cottage = cottage;
		this.rating = rating;
		this.date = date;
		this.client = client;
		this.isApproved = isApproved;
		this.revision = revision;
	}
	
	
}
