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
public class BoatPhoto {
	@Id
	@SequenceGenerator(name = "boaPhotoSeqGen", sequenceName = "boaPhotoSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "advPhotoSeqGen")
	public long id;
	@Column(length = 1200000)
	public String photo;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.EAGER)
	public Boat boat;
	
	public BoatPhoto(long id, String photo, Boat boat) {
		super();
		this.id = id;
		this.photo = photo;
		this.boat = boat;
	}
	
	public BoatPhoto(String photo, Boat boat) {
		super();
		this.photo = photo;
		this.boat = boat;
	}

	public BoatPhoto() {
		super();
	}
	
	

}
