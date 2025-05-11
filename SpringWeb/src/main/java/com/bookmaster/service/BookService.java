package com.bookmaster.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmaster.repository.dao.BookRepo;
import com.bookmaster.repository.model.BookDetails;

@Service
public class BookService {
	
	@Autowired
	BookRepo bookRepo;
	
	public List<BookDetails> getAllBook() {
		return bookRepo.findAll();
	}
	
	public BookDetails addBook(BookDetails book) {
		Objects.requireNonNull(book);
		return bookRepo.save(book);
	}
	
	public BookDetails getBookById(int id) throws Exception {
		return bookRepo.findById(id).orElseThrow(()-> new Exception( id + " is not found in the BookDetails"));
	}

	public boolean deleteBookById(int id) {
		bookRepo.deleteById(id);
		return isExists(id);
	}

	public List<BookDetails> deleteBookById(List<Integer> ids) {
		bookRepo.deleteAllById(ids);
		List<BookDetails> books = bookRepo.findAllById(ids);
		return books;
	}

	public void updateById(BookDetails updateBook) throws Exception {
//		boolean isExits = bookRepo.existsById(updateBook.getId());
//		if(isExits) {
//		}
		BookDetails getBookId = getBookById(updateBook.getId());
		System.out.println("Old Book Details: \n"+getBookId.toString());
		System.out.println("Update Book Details: \n"+updateBook.toString());
		bookRepo.saveAndFlush(updateBook);
	}

	public boolean isExists(int id) {
		return bookRepo.existsById(id);
	}
}