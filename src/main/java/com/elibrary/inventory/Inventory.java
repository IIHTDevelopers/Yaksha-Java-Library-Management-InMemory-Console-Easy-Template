package com.elibrary.inventory;

import java.util.ArrayList;
import java.util.List;

import com.elibrary.models.Book;

public class Inventory {
	public List<Book> books = new ArrayList<>();

	// Add a book to the inventory
	// Throw ISBNAlreadyExistsException, if same isbn number book is added
	public boolean addBook(Book book) {
		return false;
	}

	// Search for a book by name
	public List<Book> searchByName(String name) {
		return null;
	}

	// Search for books by author
	public List<Book> searchByAuthor(String author) {
		return null;
	}

	// Search for books by publisher
	public List<Book> searchByPublisher(String publisher) {
		return null;
	}

	// Check book availability by ISBN
	public boolean checkAvailability(String isbn) {
		return false;
	}

	// Update book details by ISBN
	// Throw BookNotFoundException if passed isbn is not found
	public boolean updateBook(String isbn, Book updatedBook) {
		return false;
	}

	// Delete a book by ISBN
	// Throw BookNotFoundException if passed isbn is not found
	public boolean deleteBook(String isbn) {
		return false;
	}
}
