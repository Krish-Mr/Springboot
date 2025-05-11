package com.bookmaster.repository.model;

import java.sql.Time;

import com.bookmaster.enums.TransactionProcessStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "transaction_details")
public class TransactionDetails {
    @Transient
    private int sid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", unique = true, precision = 10, nullable = false)
    private int transaction_id;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private BookDetails book_id; // Reference to BookDetails table

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private CustomerDetails customer_id; // Reference to CustomerDetails table

    @Column(name = "quantity", precision = 3)
    private int quantity;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private int totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private TransactionProcessStatus status;

    @Column(name = "transaction_date_time")
    private Time transactionDateTime;

    @Column(name = "remarks", length = 100)
    private String remarks;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getTransactionId() {
		return transaction_id;
	}

	public void setTransactionId(int transactionId) {
		this.transaction_id = transactionId;
	}

	public BookDetails getBookId() {
		return book_id;
	}

	public void setBookId(BookDetails bookId) {
		this.book_id = bookId;
	}

	public CustomerDetails getCustomerId() {
		return customer_id;
	}

	public void setCustomerId(CustomerDetails customerId) {
		this.customer_id = customerId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public TransactionProcessStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionProcessStatus status) {
		this.status = status;
	}

	public Time getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(Time transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

    // Getters and setters
    
}
