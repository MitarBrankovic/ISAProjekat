package com.BookingApp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class SubscribeCottage {
	@Id
	@SequenceGenerator(name = "subsCottSeqGen", sequenceName = "subsCottSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "subsCottSeqGen")
	public long id;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	public Cottage cottage;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	public Client client;
	
	
	public SubscribeCottage() {}

	public SubscribeCottage(long id, Cottage cottage, Client client) {
		this.id = id;
		this.cottage = cottage;
		this.client = client;
	}
	
	public SubscribeCottage(Cottage cottage, Client client) {
		this.cottage = cottage;
		this.client = client;
	}
	
	
}
