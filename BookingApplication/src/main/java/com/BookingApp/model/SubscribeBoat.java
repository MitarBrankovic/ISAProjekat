package com.BookingApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class SubscribeBoat {
	@Id
	@SequenceGenerator(name = "subsBoatSeqGen", sequenceName = "subsBoatSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "subsBoatSeqGen")
	public long id;
	@OneToOne
	public Boat boat;
	@Column
	public long client;
	
	
	public SubscribeBoat() {}

	public SubscribeBoat(long id, Boat boat, long client) {
		this.id = id;
		this.boat = boat;
		this.client = client;
	}
	
	public SubscribeBoat(Boat boat, long client) {
		this.boat = boat;
		this.client = client;
	}
}
