package com.tw.vapsi.biblioteca.controller;

import com.tw.vapsi.biblioteca.exception.NoBooksInLibraryException;
import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.repository.BookRepository;
import com.tw.vapsi.biblioteca.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> books() throws NoBooksInLibraryException {
        try {
            return bookService.getList();
        } catch (NoBooksInLibraryException e) {
            //
        }
        return bookService.getList();
    }

    @GetMapping("/booklist")
    public String home(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "bookList";
    }
}
