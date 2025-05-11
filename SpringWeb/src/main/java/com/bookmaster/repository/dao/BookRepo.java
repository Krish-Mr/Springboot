package com.bookmaster.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookmaster.repository.model.BookDetails;

@Repository
public interface BookRepo extends JpaRepository<BookDetails, Integer>{
	
}