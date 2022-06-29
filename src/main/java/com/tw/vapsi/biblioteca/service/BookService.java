package com.tw.vapsi.biblioteca.service;

import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class BookService {

    @Autowired
    private BookRepository bookRepository;
    public List<Book> getList() {
        return bookRepository.findAll();
    }

    public Book saveStaticData(Book book) {

        return bookRepository.save(book);
    }
}
