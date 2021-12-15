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

@Entity
public class FishingAppointment {

	@Id
	@SequenceGenerator(name = "fishingAppointmentSeqGen", sequenceName = "fishingAppointmentSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "fishingAppointmentSeqGen")
	public int id;
	@Column
	public LocalDateTime appointmentStart;
	@Column
	public String address;
	@Column
	public String city;
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
	public long price;
	@ManyToOne(fetch = FetchType.EAGER)
	public FishingAdventure fishingAdventure;
	@ManyToOne(fetch = FetchType.EAGER)
	public Client client;
	
	public FishingAppointment() {
		super();
	}
	
	public FishingAppointment(int id, LocalDateTime appointmentStart, String address, String city, long duration,
			int maxAmountOfPeople, AppointmentType appointmentType, String extraNotes, long price, int clientId) {
		super();
		this.id = id;
		this.appointmentStart = appointmentStart;
		this.address = address;
		this.city = city;
		this.duration = duration;
		this.maxAmountOfPeople = maxAmountOfPeople;
		this.appointmentType =  appointmentType;
		this.extraNotes = extraNotes;
		this.price = price;
	}
	
	
	
	
	
}
