package com.tw.vapsi.biblioteca.controller;

import com.tw.vapsi.biblioteca.exception.BookAlreadyCheckedOutException;
import com.tw.vapsi.biblioteca.exception.BookAlreadyReturnedException;
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
        }
        else{
            bookService.save(id,name,author);
        }

        return "index";
    }

    @GetMapping("/checkout/{id}")
    public String checkedOut(@PathVariable("id") long id, Model model) {

        try {
            bookService.issueCheckedOutBook(id);
            model.addAttribute("message", "Check out successful !!!");
            return "checkOutSuccess";

        } catch (UnAuthorizedUserException e) {
            model.addAttribute("errorMessage", "Unauthorized user \n Login to continue");
            return "login";

        } catch (BookNotAvailableException e) {
            model.addAttribute("errorMessage", "Book not available for checkout");
            return "booksInLibrary";

        }
        catch (BookAlreadyCheckedOutException e) {
            model.addAttribute("errorMessage", "Book already checked out");
            return "booksInLibrary";

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
            model.addAttribute("message", "Returned Book successful !!!");
            return "returnBookSuccess";

        } catch (UnAuthorizedUserException e) {

            model.addAttribute("errorMessage", "Unauthorized user \n Login to continue");
            return "login";
        }
        catch (BookNotAvailableException e)
        {
            model.addAttribute("errorMessage", "Book not Checked Out");
            return "returnBookSuccess";
        }
        catch (BookAlreadyReturnedException e)
        {
            model.addAttribute("errorMessage", "Book already returned");
            return "returnBookSuccess";
        }
    }


}