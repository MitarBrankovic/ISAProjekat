package com.BookingApp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class BoatAppointment {
	@Id
	@SequenceGenerator(name = "boatAppointmentSeqGen", sequenceName = "boatAppointmentSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "boatAppointmentSeqGen")
	public long id;
	@Column
	public LocalDateTime appointmentStart;
	@Column
	public long duration;
	@Column
	public int maxAmountOfPeople;
	@Enumerated(value = EnumType.STRING)
	@Column
	public AppointmentType appointmentType;
	@Column
	public String extraNotes;
	@Column
	public double price;
	@Column
	public double ownerProfit;
	@Column
	public double systemProfit;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.EAGER)
	public Boat boat;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.EAGER)
	public Client client;
	
	public BoatAppointment() {
		super();
	}
	public BoatAppointment(long id, LocalDateTime appointmentStart, long duration, int maxAmountOfPeople,
			AppointmentType appointmentType, String extraNotes, double price, Boat boat, Client client) {
		super();
		this.id = id;
		this.appointmentStart = appointmentStart;
		this.duration = duration;
		this.maxAmountOfPeople = maxAmountOfPeople;
		this.appointmentType = appointmentType;
		this.extraNotes = extraNotes;
		this.price = price;
		this.ownerProfit = price* 0.8;
		this.systemProfit = price* 0.2;
		this.boat = boat;
		this.client = client;
	}
	
	public BoatAppointment(LocalDateTime appointmentStart, long duration, int maxAmountOfPeople,
			AppointmentType appointmentType, String extraNotes, double price, Boat boat, Client client) {
		super();
		this.appointmentStart = appointmentStart;
		this.duration = duration;
		this.maxAmountOfPeople = maxAmountOfPeople;
		this.appointmentType = appointmentType;
		this.extraNotes = extraNotes;
		this.price = price;
		this.ownerProfit = price* 0.8;
		this.systemProfit = price* 0.2;
		this.boat = boat;
		this.client = client;
	}
}
