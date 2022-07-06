package com.tw.vapsi.biblioteca.controller;
import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
@Controller
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookService bookService;
    @GetMapping
    public String fetchBooksFromLibrary(Model model) {

        List<Book> books = bookService.fetchBooksFromLibrary();
        if (!books.isEmpty())
            model.addAttribute("books", books);
        else
            model.addAttribute("errorMessage", "Sorry, no books yet!");
        return "booksInLibrary";
    }
    @PostMapping
    public String createBook(@RequestParam @Valid long id,
                             @RequestParam @Valid String name,
                             @RequestParam @Valid String author,
                             HttpServletResponse response) {
        if(name.isEmpty() || author.isEmpty() ){
            response.setStatus(400);
        }
        else{
            bookService.save(id,name,author);
        }
        return "index";
    }
}