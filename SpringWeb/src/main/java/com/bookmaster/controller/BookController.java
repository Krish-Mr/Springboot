package com.bookmaster.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmaster.repository.model.BookDetails;
import com.bookmaster.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

@RestController
@RequestMapping(value = "/book")
public class BookController {
	/**
	 * inject the bean without using @Autowired by using @Configuration in <class>BookConfiguration.class</class>
	 */
	BookService bookServ;
	
	public BookController(BookService bookSerive) {
		bookServ = bookSerive;
	}

	@PostMapping("add")
	public ResponseEntity<BookDetails> addBook(@RequestBody BookDetails book) {
		BookDetails addBook = bookServ.addBook(book);
		if(Objects.nonNull(addBook)) {
			return ResponseEntity.status(HttpStatus.CREATED).body(addBook);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@GetMapping(path = "get/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<BookDetails> getBookById(@PathVariable int id) throws Exception {
		BookDetails book = bookServ.getBookById(id);
		if(Objects.nonNull(book)) {
			return ResponseEntity.status(HttpStatus.OK).body(book);
		}
		ResponseEntity<Object> build = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		System.out.println(new ObjectMapper().writeValueAsString(build));
		ObjectReader readerFor = new ObjectMapper().readerFor(BookDetails.class);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@GetMapping(path = "get/all-book", name = "all book", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BookDetails>> getAllBook() {
		List<BookDetails> books = bookServ.getAllBook();
		if( Objects.nonNull(books) )
			return ResponseEntity.status(HttpStatus.FOUND).body(books);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(books);
	}

	public void updateBookById() {
		
	}

	@DeleteMapping(path = "delete/{id}")
	public ResponseEntity<String> deleteBookById(@PathVariable int id) {
		bookServ.deleteBookById(id);
		if(bookServ.isExists(id))
			return ResponseEntity.status(HttpStatus.ACCEPTED).body( id+ " is deleted successfully");
		else
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body( id+ " is deleted unsuccessfully");
	}
 
	@DeleteMapping(path = "delete/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	public void deleteBookByIds(List<Integer> ids) {
		bookServ.deleteBookById(ids);
	}
}