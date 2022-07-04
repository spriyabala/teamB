package com.tw.vapsi.biblioteca.controller;

import com.tw.vapsi.biblioteca.exception.BookNotAvailableException;
import com.tw.vapsi.biblioteca.exception.UnAuthorizedUserException;

import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.service.CheckedOutBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/checkout")
public class CheckedOutBooksController {
    @Autowired
    private CheckedOutBooksService checkedOutBooksService;


    @GetMapping("/{id}")
    public String checkedOutBook(@PathVariable("id") long id, Model model) {

        try {
            checkedOutBooksService.checkUserAccess();
            Book book = checkedOutBooksService.bookAvailableInLibrary(id);
            checkedOutBooksService.saveCheckoutDetails(id);
            checkedOutBooksService.updateAvailableFlag(book);
            model.addAttribute("message", "Check out successful !!!");
            return "checkoutsuccess";

        } catch (UnAuthorizedUserException e) {
            model.addAttribute("errorMessage", "Unauthorized user \n Login to continue");
            return "login";

        } catch (BookNotAvailableException e) {
            model.addAttribute("errorMessage", "Book not available");
            return "booksInLibrary";
        }
    }

    @GetMapping
    public String viewCheckOutBooks(Model model) {
        try {
            checkedOutBooksService.checkUserAccess();
            List<Book> books = checkedOutBooksService.checkedOutBookDetails();
            model.addAttribute("books", books);
            return "checkOutBooks";

        } catch (UnAuthorizedUserException e) {
            model.addAttribute("errorMessage", "Unauthorized user \n Login to continue");
            return "login";

        }
    }


}