package com.bookmaster.rest.person;

public class RecordExample {
	public static void main(String[] args) {
		Address address1 = new Address(21, "VVG Nagar", "Venagamedu", "Karur", "TamilNadu");
		Person p1 = new Person(1, "Gokul", "CSE", 30_000.5, address1);
		//Does not support setter because its immutable. Only Declared in Constructor
		System.out.println(p1.name());
		System.out.println(p1.address().district());

		p1 = null;
		System.out.println(p1);
	}
}
