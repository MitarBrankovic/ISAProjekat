package com.BookingApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Complaint {
	@Id
	@SequenceGenerator(name = "complaintSeqGen", sequenceName = "complaintSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "complaintSeqGen")
	private long id;
	@Column
	public String text;
	@OneToOne
	public Cottage cottage;
	@OneToOne
	public AppUser appUser;
	/*@Column
	public long cottage_id;
	@Column
	public long app_user_id;
	*/
	
	public Complaint() {
		super();
	}

	public Complaint(String text ,Cottage cottage, AppUser appUser) {
		super();
		this.text = text;
		this.cottage = cottage;
		this.appUser = appUser;
		//this.cottage_id = cottage.
	}

	public Complaint(long id, String text) {
		super();
		this.id = id;
		this.text = text;
	}
	
	
}
