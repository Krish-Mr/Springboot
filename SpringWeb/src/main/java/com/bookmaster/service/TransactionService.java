package com.bookmaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmaster.repository.dao.TransactionRepo;

@Service
public class TransactionService {
	@Autowired
	private TransactionRepo tranRepo;
}
