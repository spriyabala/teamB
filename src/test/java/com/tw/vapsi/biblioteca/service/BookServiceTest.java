package com.tw.vapsi.biblioteca.service;

import com.tw.vapsi.biblioteca.exception.NoBooksInLibraryException;
import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest

class   BookServiceTest {
    @MockBean
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;

    @Test
    void shouldGetListOfBooks() throws NoBooksInLibraryException {
        List<Book> books = new ArrayList<>();
        books.add(new Book("The River of Adventures","Enid Blyton"));
        books.add(new Book("Muniya","NBT Publications"));
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> list=bookService.getList();
        assertEquals(books,list);
        verify(bookRepository,times(1)).findAll();

    }

    @Test
    void shouldDisplayMessageWhenNoBooksAvailable() {
        List<Book> books = new ArrayList<>();
        when(bookRepository.findAll()).thenReturn(books);

        assertEquals(books,bookService.getList());
        verify(bookRepository,times(1)).findAll();

    }


}