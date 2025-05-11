package com.bookmaster.repository.model;

import java.sql.Date;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @Entity - Enables table representation in Java using JPA
 */
@Entity
@Table(name = "customer_details")
public class CustomerDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false, unique = true, precision = 10)
    private int id;

    @Column(name = "name", nullable = false, unique = false, length = 20)
    private String name;

    @Column(name = "dob")
    private Date date;

    @Column(name = "age", nullable = false, precision = 10)
    private int age;

    // m,f,o
    @Column(name = "gender", nullable = false, length = 1)
    private Character gender;

    @Embedded
    @AttributeOverrides({ 
        @AttributeOverride(name = "city", column = @Column(name = "city", length = 20)),
        @AttributeOverride(name = "state", column = @Column(name = "state", length = 20)),
        @AttributeOverride(name = "country", column = @Column(name = "country", length = 20)),
        @AttributeOverride(name = "pincode", column = @Column(name = "pincode", length = 10))
    })
    private Address address;

    @Column(name = "phone_no", length = 10, nullable = true)
    private int phNo;

    @Column(name = "email", length = 50, nullable = true)
    private String email;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getPhNo() {
		return phNo;
	}

	public void setPhNo(int phNo) {
		this.phNo = phNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    
}
