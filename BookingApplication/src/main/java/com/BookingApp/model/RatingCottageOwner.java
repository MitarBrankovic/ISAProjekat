package com.BookingApp.model;

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
public class RatingCottageOwner {

	@Id
	@SequenceGenerator(name = "myRatingCottOwnerSeqGen", sequenceName = "myRatingCottOwnerSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "myRatingCottOwnerSeqGen")
	public long id;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	public CottageOwner cottageOwner;
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
	
	public RatingCottageOwner() {}

	public RatingCottageOwner(long id, CottageOwner cottageOwner, double rating, LocalDateTime date, AppUser client, boolean isApproved, String revision) {
		super();
		this.id = id;
		this.cottageOwner = cottageOwner;
		this.rating = rating;
		this.date = date;
		this.client = client;
		this.isApproved = isApproved;
		this.revision = revision;
	}
	
	public RatingCottageOwner(CottageOwner cottageOwner, double rating, LocalDateTime date, AppUser client, boolean isApproved, String revision) {
		super();
		this.cottageOwner = cottageOwner;
		this.rating = rating;
		this.date = date;
		this.client = client;
		this.isApproved = isApproved;
		this.revision = revision;
	}
}
