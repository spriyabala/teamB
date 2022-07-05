package com.tw.vapsi.biblioteca.controller;

import com.tw.vapsi.biblioteca.exception.BookNotAvailableException;
import com.tw.vapsi.biblioteca.exception.UnAuthorizedUserException;
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


    @PostConstruct
    public void loadStaticData() {
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

    @PostMapping
    public String createBook(@RequestParam @Valid long id,
                             @RequestParam @Valid String name,
                             @RequestParam @Valid String author,
                             HttpServletResponse response) {
        if (name.isEmpty() || author.isEmpty()) {
            response.setStatus(400);
            //  return "Incomplete Input";
        } else {
            bookService.save(id, name, author);
        }
        return "index";
    }

    @GetMapping("/checkout/{id}")
    public String checkedOutBook(@PathVariable("id") long id, Model model) {

        try {
            bookService.issueCheckedOutBook(id);

            model.addAttribute("message", "Check out successful !!!");
            return "checkOutSuccess";

        } catch (UnAuthorizedUserException e) {
            model.addAttribute("errorMessage", "Unauthorized user \n Login to continue");
            return "login";

        }
        catch (BookNotAvailableException e) {
            model.addAttribute("errorMessage", "Book not available for checkout");
            return "login";

        }
    }

    @GetMapping("/checkout")
    public String viewCheckOutBooks(Model model) {
        try {
            List<Book> books = bookService.getCheckedOutBooks();
            model.addAttribute("books", books);
            return "checkOutBooks";

        } catch (UnAuthorizedUserException e) {
            model.addAttribute("errorMessage", "Unauthorized user \n Login to continue");
            return "login";

        }
    }

    @GetMapping("/return/{id}")
    public String returnBook(@PathVariable("id") long id, Model model) {

        try {

            bookService.returnBook(id);
            /*returnBookService.checkUserAccess();
            //  Book book =checkedOutBooksService.bookAvailableInLibrary(id);
            returnBookService.removeACheckedOutBook(id);
            returnBookService.updateAvailableFlag(null);*/
            model.addAttribute("message", "Returned Book successful !!!");
            return "returnBookSuccess";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Book not Checked Out");
            return "booksInLibrary";
        }

    }






}