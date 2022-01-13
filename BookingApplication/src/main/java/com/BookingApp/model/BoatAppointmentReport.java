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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class BoatAppointmentReport {
	@Id
	@SequenceGenerator(name = "boatReportSeqGen", sequenceName = "boatReportSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "boatReportSeqGen")
	public long id;
	@Column
	public String comment;
	@Enumerated(value = EnumType.STRING)
	@Column
	public AppointmentRatingOptions ratingOption;
	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	public AppUser client;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	public AppUser owner;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	public BoatAppointment appointment;
	@Column
	public boolean isApproved;
	
	public BoatAppointmentReport(long id, String comment, AppointmentRatingOptions ratingOption, AppUser client, AppUser owner,
			BoatAppointment appointment, boolean isApproved) {
		super();
		this.id = id;
		this.comment = comment;
		this.ratingOption = ratingOption;
		this.client = client;
		this.owner = owner;
		this.appointment = appointment;
		this.isApproved = isApproved;
	}
	
	public BoatAppointmentReport(String comment, AppointmentRatingOptions ratingOption, AppUser client, AppUser owner,
			BoatAppointment appointment, boolean isApproved) {
		super();
		this.comment = comment;
		this.ratingOption = ratingOption;
		this.client = client;
		this.owner = owner;
		this.appointment = appointment;
		this.isApproved = isApproved;
	}

	public BoatAppointmentReport() {
		super();
	}
	
}
