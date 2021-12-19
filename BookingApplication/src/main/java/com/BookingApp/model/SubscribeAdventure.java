package com.BookingApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class SubscribeAdventure {
	@Id
	@SequenceGenerator(name = "subsAdvSeqGen", sequenceName = "subsAdvSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "subsAdvSeqGen")
	public long id;
	@OneToOne
	public FishingAdventure fishingAdventure;
	@Column
	public long client;
	
	
	public SubscribeAdventure() {}

	public SubscribeAdventure(long id, FishingAdventure fishingAdventure, long client) {
		this.id = id;
		this.fishingAdventure = fishingAdventure;
		this.client = client;
	}
	
	public SubscribeAdventure(FishingAdventure fishingAdventure, long client) {
		this.fishingAdventure = fishingAdventure;
		this.client = client;
	}
}
