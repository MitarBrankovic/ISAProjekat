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
public class AdventurePhoto {
	@Id
	@SequenceGenerator(name = "advPhotoSeqGen", sequenceName = "advPhotoSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "advPhotoSeqGen")
	public long id;
	@Column(length = 1200000)
	public String photo;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.EAGER)
	public FishingAdventure fishingAdventure;
	
	public AdventurePhoto(long id, String photo, FishingAdventure fishingAdventure) {
		super();
		this.id = id;
		this.photo = photo;
		this.fishingAdventure = fishingAdventure;
	}
	
	public AdventurePhoto(String photo, FishingAdventure fishingAdventure) {
		super();
		this.photo = photo;
		this.fishingAdventure = fishingAdventure;
	}

	public AdventurePhoto() {
		super();
	}
	
	

}
