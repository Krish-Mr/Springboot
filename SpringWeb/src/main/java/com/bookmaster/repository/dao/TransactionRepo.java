package com.bookmaster.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookmaster.repository.model.TransactionDetails;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionDetails, Integer>{

}
