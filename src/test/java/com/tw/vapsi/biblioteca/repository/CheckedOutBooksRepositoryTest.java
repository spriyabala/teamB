package com.tw.vapsi.biblioteca.repository;

import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.model.CheckedOutBooks;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class CheckedOutBooksRepositoryTest {

    @Autowired
    private CheckedOutBooksRepository checkedOutBooksRepository;

    @Autowired
    private BookRepository bookRepository;


    @Test
    void shouldReturnCheckOutBookListByAGivenEmail() {
        CheckedOutBooks expectedBook = new CheckedOutBooks(1,"email@example.com");
        expectedBook.setId(1L);
        checkedOutBooksRepository.save(expectedBook);
        Book book = new Book("New Book","New Author");
        book.setId(1);
        bookRepository.save(book);
        List<Book> books = checkedOutBooksRepository.findCheckedOutBook("email@example.com");
        assertEquals(1, books.get(0).getId());
        assertEquals("New Book",  books.get(0).getName());
        assertEquals("New Author",  books.get(0).getAuthor());

    }

    @Test
    void shouldNotReturnCheckOutBookListByAGivenEmail() {
        CheckedOutBooks expectedBook = new CheckedOutBooks(1,"email@example.com");
        expectedBook.setId(1L);
        checkedOutBooksRepository.save(expectedBook);
        Book book = new Book("New Book","New Author");
        book.setId(1);
        bookRepository.save(book);

        List<Book> checkOutBooks = checkedOutBooksRepository.findCheckedOutBook("email-different@example.com");
        assertTrue(checkOutBooks.isEmpty());


    }




}