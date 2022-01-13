package com.BookingApp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class SubscribeAdventure {
	@Id
	@SequenceGenerator(name = "subsAdvSeqGen", sequenceName = "subsAdvSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "subsAdvSeqGen")
	public long id;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	public FishingAdventure fishingAdventure;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	public Client client;
	
	
	public SubscribeAdventure() {}

	public SubscribeAdventure(long id, FishingAdventure fishingAdventure, Client client) {
		this.id = id;
		this.fishingAdventure = fishingAdventure;
		this.client = client;
	}
	
	public SubscribeAdventure(FishingAdventure fishingAdventure, Client client) {
		this.fishingAdventure = fishingAdventure;
		this.client = client;
	}
}
