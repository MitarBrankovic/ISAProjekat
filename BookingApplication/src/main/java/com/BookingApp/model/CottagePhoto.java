package com.BookingApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class CottagePhoto {
	@Id
	@SequenceGenerator(name = "cotPhotoSeqGen", sequenceName = "cotPhotoSeqGen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "advPhotoSeqGen")
	public long id;
	@Column(length = 1200000)
	public String photo;
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.EAGER)
	public Cottage cottage;
	
	public CottagePhoto(long id, String photo, Cottage cottage) {
		super();
		this.id = id;
		this.photo = photo;
		this.cottage = cottage;
	}
	
	public CottagePhoto(String photo, Cottage cottage) {
		super();
		this.photo = photo;
		this.cottage = cottage;
	}

	public CottagePhoto() {
		super();
	}
	
	

}
