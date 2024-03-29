package com.BookingApp.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
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
import javax.persistence.Version;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class CottageAppointment {
	@Id
	@SequenceGenerator(name = "cottageAppointmentSeqGen", sequenceName = "cottageAppointmentSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cottageAppointmentSeqGen")
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
	public Cottage cottage;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.EAGER)
	public Client client;
	
    @Version
    public Long version;
	
	public CottageAppointment() {
		super();
	}
	public CottageAppointment(long id, LocalDateTime appointmentStart, long duration, int maxAmountOfPeople,
			AppointmentType appointmentType, String extraNotes, double price, Cottage cottage, Client client) {
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
		this.cottage = cottage;
		this.client = client;
	}
	
	public CottageAppointment(LocalDateTime appointmentStart, long duration, int maxAmountOfPeople,
			AppointmentType appointmentType, String extraNotes, double price, Cottage cottage, Client client) {
		super();
		this.appointmentStart = appointmentStart;
		this.duration = duration;
		this.maxAmountOfPeople = maxAmountOfPeople;
		this.appointmentType = appointmentType;
		this.extraNotes = extraNotes;
		this.price = price;
		this.ownerProfit = price* 0.8;
		this.systemProfit = price* 0.2;
		this.cottage = cottage;
		this.client = client;
	}
	public CottageAppointment(LocalDateTime appointmentStart, long duration, int maxAmountOfPeople,
			AppointmentType appointmentType, String extraNotes, double price, double ownerProfit, double systemProfit) {
		super();
		this.appointmentStart = appointmentStart;
		this.duration = duration;
		this.maxAmountOfPeople = maxAmountOfPeople;
		this.appointmentType = appointmentType;
		this.extraNotes = extraNotes;
		this.price = price;
		this.ownerProfit = ownerProfit;
		this.systemProfit = systemProfit;
	}
	
	
}
