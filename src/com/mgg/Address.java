package com.mgg;

/*
 * This is the Address class
 * Stores home and store address
 * Using constructor with street, city, state, zip and country
 * 
 */

public class Address {
	
	private Integer addressId;
	private String street;
	private String city;
	private String state;
	private String ZIP;
	private String country;
	
	
	public Address(Integer addressId, String street, String city, String state, String zIP, String country) {
		super();
		this.addressId = addressId;
		this.street = street;
		this.city = city;
		this.state = state;
		ZIP = zIP;
		this.country = country;
	}
	
	public Address(String street, String city, String state, String zIP, String country) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.ZIP = zIP;
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZIP() {
		return ZIP;
	}

	public String getCountry() {
		return country;
	}

	public Integer getAddressId() {
		return addressId;
	}

	@Override
	public String toString() {
		return street + " " + city + " " + state + " \n" + ZIP + " "
				+ country;
	}
	
	

}