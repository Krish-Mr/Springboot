package com.bookmaster.repository.model;


import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * The table was already created just specify the @Data annotation for @Getter and @Setter
 */
@Entity
public class BookDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String author;
	private Date releaseDate;
	private double prize;
	public BookDetails(){}
	public BookDetails(int id, String author, String title, Date releaseDate, double prize) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.prize = prize;
		this.releaseDate = releaseDate;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getRelease_date() {
		return releaseDate;
	}
	public void setRelease_date(Date release_date) {
		this.releaseDate = release_date;
	}
	public double getPrize() {
		return prize;
	}
	public void setPrize(double prize) {
		this.prize = prize;
	}
	
	@Override
	public String toString() {
		return "{Id: "+this.id + 
				" ,Title: "+this.title +
				", Author: "+this.author + 
				", Release_date: "+ this.releaseDate + 
				", Prize: "+this.prize + 
				"}";
	}

}
