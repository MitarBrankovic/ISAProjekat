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
public class FishingAppointment {

	@Id
	@SequenceGenerator(name = "fishingAppointmentSeqGen", sequenceName = "fishingAppointmentSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "fishingAppointmentSeqGen")
	public long id;
	@Column
	public LocalDateTime appointmentStart;
	@Column
	public String address;
	@Column
	public String city;
	@Column
	public long duration;
	@Column
	public long maxAmountOfPeople;
	@Enumerated(value = EnumType.STRING)
	@Column
	public AppointmentType appointmentType;
	@Column
	public boolean available;
	@Column
	public double rating;
	@Column
	public String extraNotes;
	@Column
	public double price;
	@Column
	public double instructorProfit;
	@Column
	public double systemProfit;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.EAGER)
	public FishingAdventure fishingAdventure;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.EAGER)
	public Client client;
	
	public FishingAppointment() {}
	
	public FishingAppointment(long id, LocalDateTime appointmentStart, String address, String city, long duration,
			long maxAmountOfPeople, AppointmentType appointmentType, boolean available, double rating, String extraNotes, double price) {
		super();
		this.id = id;
		this.appointmentStart = appointmentStart;
		this.address = address;
		this.city = city;
		this.duration = duration;
		this.maxAmountOfPeople = maxAmountOfPeople;
		this.appointmentType =  appointmentType;
		this.available = available;
		this.rating = rating;
		this.extraNotes = extraNotes;
		this.price = price;
		this.instructorProfit = price* 0.8;
		this.systemProfit = price* 0.2;
	}
	
	public FishingAppointment(LocalDateTime appointmentStart, String address, String city, long duration,
			long maxAmountOfPeople, AppointmentType appointmentType, boolean available, double rating, String extraNotes, double price) {
		super();
		this.appointmentStart = appointmentStart;
		this.address = address;
		this.city = city;
		this.duration = duration;
		this.maxAmountOfPeople = maxAmountOfPeople;
		this.appointmentType =  appointmentType;
		this.available = available;
		this.rating = rating;
		this.extraNotes = extraNotes;
		this.price = price;
		this.instructorProfit = price* 0.8;
		this.systemProfit = price* 0.2;
	}
	
	
	
	
	
}
