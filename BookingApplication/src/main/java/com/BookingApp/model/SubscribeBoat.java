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
public class SubscribeBoat {
	@Id
	@SequenceGenerator(name = "subsBoatSeqGen", sequenceName = "subsBoatSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "subsBoatSeqGen")
	public long id;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	public Boat boat;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	public Client client;
	
	
	public SubscribeBoat() {}

	public SubscribeBoat(long id, Boat boat, Client client) {
		this.id = id;
		this.boat = boat;
		this.client = client;
	}
	
	public SubscribeBoat(Boat boat, Client client) {
		this.boat = boat;
		this.client = client;
	}
}
