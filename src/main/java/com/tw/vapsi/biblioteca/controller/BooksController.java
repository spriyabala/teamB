package com.tw.vapsi.biblioteca.controller;

import com.tw.vapsi.biblioteca.exception.NoBooksInLibraryException;
import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.repository.BookRepository;
import com.tw.vapsi.biblioteca.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String home(Model model) {
        try {
            model.addAttribute("books", bookService.getList());
            return "bookList";
        } catch (NoBooksInLibraryException e) {
            return "noBooks";
        }

    }


}
