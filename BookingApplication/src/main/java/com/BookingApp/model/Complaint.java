package com.BookingApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Complaint {
	@Id
	@SequenceGenerator(name = "complaintSeqGen", sequenceName = "complaintSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "complaintSeqGen")
	public long id;
	@Column
	public String text;
	@Column
	public long entityId;
	@Column
	public long owner;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	public AppUser client;
	
	public Complaint() {
		super();
	}

	public Complaint(String text ,long entityId, long owner, AppUser client) {
		super();
		this.text = text;
		this.entityId = entityId;
		this.owner = owner;
		this.client = client;
	}

	public Complaint(long id, String text) {
		super();
		this.id = id;
		this.text = text;
	}
	
	
}
