package com.bookmaster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmaster.repository.dao.CustomerRepo;
import com.bookmaster.repository.model.CustomerDetails;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepo cusRepo;

	public void getAllRecords() {
		List<CustomerDetails> allRecords = cusRepo.findAll();
		System.out.println(allRecords + "\t: "+ allRecords.toString());
	}
}