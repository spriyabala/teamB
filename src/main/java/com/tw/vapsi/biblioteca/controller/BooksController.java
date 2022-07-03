package com.tw.vapsi.biblioteca.controller;
import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import java.util.List;
@Controller
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookService bookService;

    @PostConstruct
    public void loadStaticData() {
        bookService.saveStaticData(new Book("The Adventure Of River", "Enid Blyton"));
        bookService.saveStaticData(new Book("Rusty Runs Away", "Ruskin Bond"));
        bookService.saveStaticData(new Book("Who Will Cry When you Die?", "Robin Sharma"));
        bookService.saveStaticData(new Book("Fear Not: Be Strong", "Swami Vivekananda"));
        bookService.saveStaticData(new Book("The Girl on the Train", "Paula Hawkins"));
        bookService.saveStaticData(new Book("The Help", "Kathryn Stockett"));
        bookService.saveStaticData(new Book("Gone Girl", "Gillian Flynn"));
        bookService.saveStaticData(new Book("The Notebook", "Nicholas Sparks"));
        bookService.saveStaticData(new Book("In a Dark, Dark Wood", "Ruth Ware"));
        bookService.saveStaticData(new Book("Me Before You", "Jojo Moyes"));
    }

    @GetMapping
    public String fetchBooksFromLibrary(Model model) {

        List<Book> books = bookService.fetchBooksFromLibrary();
        if (!books.isEmpty())
            model.addAttribute("books", books);
        else
            model.addAttribute("errorMessage", "Sorry, no books yet!");
        return "booksInLibrary";
    }

}