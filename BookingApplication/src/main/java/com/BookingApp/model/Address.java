package com.BookingApp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Address {
	@Id
	private long id;
	private String street;
	private int number;
	private String city; //pretpostavljamo da je sve u Srbiji
	private double longitude; //geo duzina
	private double latitude; //geo sirina
	private int zipCode;
	
	
	public Address() {
		super();
	}


	public Address(String street, int number, String city, double longitude, double latitude, int zipCode) {
		super();
		this.street = street;
		this.number = number;
		this.city = city;
		this.longitude = longitude;
		this.latitude = latitude;
		this.zipCode = zipCode;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public int getZipCode() {
		return zipCode;
	}


	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	
	
	
}
