package com.BookingApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class CottageAppointmentReport {
	@Id
	@SequenceGenerator(name = "cottageReportSeqGen", sequenceName = "cottageReportSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cottageReportSeqGen")
	public long id;
	@Column
	public String comment;
	@Enumerated(value = EnumType.STRING)
	@Column
	public AppointmentRatingOptions ratingOption;
	@OneToOne
	public AppUser client;
	@OneToOne
	public AppUser owner;
	@OneToOne
	public CottageAppointment appointment;
	@Column
	public boolean isApproved;
	
	public CottageAppointmentReport(long id, String comment, AppointmentRatingOptions ratingOption, AppUser client, AppUser owner,
			CottageAppointment appointment, boolean isApproved) {
		super();
		this.id = id;
		this.comment = comment;
		this.ratingOption = ratingOption;
		this.client = client;
		this.owner = owner;
		this.appointment = appointment;
		this.isApproved = isApproved;
	}
	
	public CottageAppointmentReport(String comment, AppointmentRatingOptions ratingOption, AppUser client, AppUser owner,
			CottageAppointment appointment, boolean isApproved) {
		super();
		this.comment = comment;
		this.ratingOption = ratingOption;
		this.client = client;
		this.owner = owner;
		this.appointment = appointment;
		this.isApproved = isApproved;
	}

	public CottageAppointmentReport() {
		super();
	}
}
