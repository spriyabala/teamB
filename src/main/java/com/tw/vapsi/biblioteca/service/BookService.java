package com.tw.vapsi.biblioteca.service;

import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    public List<Book> fetchBooksFromLibrary() {
        return bookRepository.findAll();
    }
   /* public Book saveStaticData(Book book) {
        return bookRepository.save(book);
    }
*/
    public Book save(long id, String name, String author) {
        Book book = new Book(id,name,author);
        return bookRepository.save(book);
    }

}