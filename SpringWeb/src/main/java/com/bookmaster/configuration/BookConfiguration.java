package com.bookmaster.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bookmaster.controller.BookController;
import com.bookmaster.repository.dao.BookRepo;
import com.bookmaster.service.BookService;

@Configuration
public class BookConfiguration {
	@Bean
	public BookService bookSerive(){
		return new BookService();
	}

	@Bean 
	public BookController bookController() {
		return new BookController(bookSerive());
	}
}
