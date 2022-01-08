package com.BookingApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AppUser {

	@Id
	@SequenceGenerator(name = "myUserSeqGen", sequenceName = "myUserSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myUserSeqGen")
	@Column(name="id", unique=true, nullable=false)
	public long id;
	@Column
	public String name;
	@Column
	public String surname;
	@Column(nullable = false, unique = true)
	public String email;
	@Column
	public String password;
	@Column
	public String address;
	@Column
	public String city;
	@Column
	public String country;
	@Column
	public String phoneNumber;
	@Enumerated(value = EnumType.STRING)
	@Column
	public UserType role;
	@Column
	public String verificationCode;
	@Column
	public Boolean verified;
	@Column
	public long loyaltyPoints;
	@Enumerated(value = EnumType.STRING)
	@Column
	public LoyaltyStatus loyaltyStatus;
	
	public AppUser() {
		super();
	}
	
	public AppUser(long id, String name, String surname, String email, String password, String address, String city,
			String country, String phoneNumber, UserType role, String verificationCode, Boolean firstLogin) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.verificationCode = verificationCode;
		this.verified = firstLogin;
		loyaltyPoints = 0;
		loyaltyStatus = LoyaltyStatus.bronze;
	}
	
	public AppUser(String name, String surname, String email, String password, String address, String city,
			String country, String phoneNumber, UserType role, String verificationCode, Boolean firstLogin) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.verificationCode = verificationCode;
		this.verified = firstLogin;
		loyaltyPoints = 0;
		loyaltyStatus = LoyaltyStatus.bronze;
	}

	public AppUser(long id, String name, String surname, String email, String password, String address, String city,
			String country, String phoneNumber, UserType role, String verificationCode, Boolean verified,
			long loyaltyPoints, LoyaltyStatus loyaltyStatus) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.verificationCode = verificationCode;
		this.verified = verified;
		this.loyaltyPoints = loyaltyPoints;
		this.loyaltyStatus = loyaltyStatus;
	}
	
	
	
}
