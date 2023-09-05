package com.elibrary.functional;

import static com.elibrary.testutils.TestUtils.businessTestFile;
import static com.elibrary.testutils.TestUtils.currentTest;
import static com.elibrary.testutils.TestUtils.testReport;
import static com.elibrary.testutils.TestUtils.yakshaAssert;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.elibrary.exception.BookNotFoundException;
import com.elibrary.exception.ISBNAlreadyExistsException;
import com.elibrary.inventory.Inventory;
import com.elibrary.models.Book;

public class FunctionalTest {

	private Inventory inventory;

	@BeforeEach
	public void setUp() {
		inventory = new Inventory();
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testAddBook() throws IOException {
		try {
			int previousSize = inventory.books.size();
			Book book1 = new Book("ISBN123", "Book 1", "Author 1", "Publisher 1", true, null, null);
			boolean result = inventory.addBook(book1);
			yakshaAssert(currentTest(), result == true && inventory.books.size() == previousSize + 1, businessTestFile);
		} catch (BookNotFoundException ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testAddBookThrowsISBNAlreadyExistsException() throws IOException {
		try {
			Book book1 = new Book("ISBN123", "Book 1", "Author 1", "Publisher 1", true, null, null);
			inventory.addBook(book1);
			Book book2 = new Book("ISBN123", "Book 2", "Author 2", "Publisher 2", true, null, null);
			inventory.addBook(book2);
			yakshaAssert(currentTest(), false, businessTestFile);
		} catch (ISBNAlreadyExistsException ex) {
			yakshaAssert(currentTest(), true, businessTestFile);
		}
	}

	@Test
	public void testSearchByName() throws IOException {
		try {
			Book book1 = new Book("ISBN123", "Book 1", "Author 1", "Publisher 1", true, null, null);
			Book book2 = new Book("ISBN456", "Book 2", "Author 2", "Publisher 2", true, null, null);
			inventory.addBook(book1);
			inventory.addBook(book2);
			yakshaAssert(currentTest(), inventory.searchByName("Book 1").size() == 1
					&& inventory.searchByName("Book 2").size() == 1 && inventory.searchByName("Book 3").size() == 0,
					businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testSearchByAuthor() throws IOException {
		try {
			Book book1 = new Book("ISBN123", "Book 1", "Author 1", "Publisher 1", true, null, null);
			Book book2 = new Book("ISBN456", "Book 2", "Author 2", "Publisher 2", true, null, null);
			inventory.addBook(book1);
			inventory.addBook(book2);
			yakshaAssert(currentTest(),
					inventory.searchByAuthor("Author 1").size() == 1 && inventory.searchByAuthor("Author 2").size() == 1
							&& inventory.searchByAuthor("Author 3").size() == 0,
					businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testSearchByPublisher() throws IOException {
		try {
			Book book1 = new Book("ISBN123", "Book 1", "Author 1", "Publisher 1", true, null, null);
			Book book2 = new Book("ISBN456", "Book 2", "Author 2", "Publisher 2", true, null, null);
			inventory.addBook(book1);
			inventory.addBook(book2);
			yakshaAssert(currentTest(),
					inventory.searchByPublisher("Publisher 1").size() == 1
							&& inventory.searchByPublisher("Publisher 2").size() == 1
							&& inventory.searchByPublisher("Publisher 3").size() == 0,
					businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testCheckAvailability() throws IOException {
		try {
			Book book1 = new Book("ISBN123", "Book 1", "Author 1", "Publisher 1", true, null, null);
			Book book2 = new Book("ISBN456", "Book 2", "Author 2", "Publisher 2", false, null, null);
			inventory.addBook(book1);
			inventory.addBook(book2);
			yakshaAssert(currentTest(),
					inventory.checkAvailability("ISBN123") == true && inventory.checkAvailability("ISBN456") == false
							&& inventory.checkAvailability("ISBN789") == false,
					businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testUpdateBook() throws IOException {
		try {
			Book book1 = new Book("ISBN123", "Book 1", "Author 1", "Publisher 1", true, null, null);
			inventory.addBook(book1);
			Book updatedBook = new Book("ISBN123", "Updated Book", "Updated Author", "Updated Publisher", false, null,
					null);
			inventory.updateBook("ISBN123", updatedBook);
			yakshaAssert(currentTest(), inventory.checkAvailability("ISBN123") == false
					&& inventory.searchByAuthor("Updated Author").size() == 1, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testUpdateBookThrowsBookNotFoundException() throws IOException {
		try {
			Book book1 = new Book("ISBN123", "Book 1", "Author 1", "Publisher 1", true, null, null);
			inventory.addBook(book1);
			Book updatedBook = new Book("ISBN123", "Updated Book", "Updated Author", "Updated Publisher", false, null,
					null);
			inventory.updateBook("EWE123", updatedBook);
			yakshaAssert(currentTest(), false, businessTestFile);
		} catch (BookNotFoundException ex) {
			yakshaAssert(currentTest(), true, businessTestFile);
		}
	}

	@Test
	public void testDeleteBook() throws IOException {
		try {
			Book book1 = new Book("ISBN123", "Book 1", "Author 1", "Publisher 1", true, null, null);
			inventory.addBook(book1);
			boolean result = inventory.deleteBook("ISBN123");
			yakshaAssert(currentTest(), result, businessTestFile);
		} catch (Exception ex) {
			ex.printStackTrace();
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testDeleteBookThrowsBookNotFoundException() throws IOException {
		try {
			Book book1 = new Book("ISBN123", "Book 1", "Author 1", "Publisher 1", true, null, null);
			inventory.addBook(book1);
			inventory.deleteBook("ISBN123");
			inventory.deleteBook("ISBN123");
			yakshaAssert(currentTest(), false, businessTestFile);
		} catch (BookNotFoundException ex) {
			yakshaAssert(currentTest(), true, businessTestFile);
		}
	}
}
