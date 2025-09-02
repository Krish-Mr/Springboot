package com.bookmaster.rest.person;

public record Address(int doorNo, String street, String city, String district, String state) { 
	public void print() {
		System.out.println("Print Address");
	}
}
