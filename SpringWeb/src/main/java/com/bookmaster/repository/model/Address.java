package com.bookmaster.repository.model;

import jakarta.persistence.Embeddable;


@SuppressWarnings("unused")
@Embeddable
public class Address {
	private String city;
	private String state;
	private String country;
	private int pincode;
}
