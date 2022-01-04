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
	public long price;
	@ManyToOne(fetch = FetchType.EAGER)
	public Cottage cottage;
	@ManyToOne(fetch = FetchType.EAGER)
	public Client client;
	
	public CottageAppointment() {
		super();
	}
	public CottageAppointment(long id, LocalDateTime appointmentStart, long duration, int maxAmountOfPeople,
			AppointmentType appointmentType, String extraNotes, long price, Cottage cottage, Client client) {
		super();
		this.id = id;
		this.appointmentStart = appointmentStart;
		this.duration = duration;
		this.maxAmountOfPeople = maxAmountOfPeople;
		this.appointmentType = appointmentType;
		this.extraNotes = extraNotes;
		this.price = price;
		this.cottage = cottage;
		this.client = client;
	}
}
