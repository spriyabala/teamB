package com.tw.vapsi.biblioteca.controller;

import com.tw.vapsi.biblioteca.exception.BookNotAvailableException;
import com.tw.vapsi.biblioteca.exception.BookNotReturnedException;
import com.tw.vapsi.biblioteca.exception.UnAuthorizedUserException;
import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.service.CheckedOutBooksService;
import com.tw.vapsi.biblioteca.service.ReturnBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/return")
public class ReturnBooksController {
    @Autowired
    private ReturnBookService returnBookService;
    @Autowired
    private CheckedOutBooksService checkedOutBooksService;

    @GetMapping("/{id}")
    public String returnBook(@PathVariable("id") long id, Model model) {

        try {
            returnBookService.checkUserAccess();
            Book book =checkedOutBooksService.bookAvailable(id);
            returnBookService.removeACheckedOutBook(id);
            returnBookService.updateAvailableFlag(book);
            model.addAttribute("message", "Returned Book successful !!!");
            return "returnBookSuccess";

        } catch (UnAuthorizedUserException e) {
            model.addAttribute("errorMessage", "Unauthorized user \n Login to continue");
            return "login";

        } catch (BookNotAvailableException e) {
            model.addAttribute("errorMessage", "Book not Checked Out");
            return "booksInLibrary";
        } catch (BookNotReturnedException e) {
            model.addAttribute("errorMessage", "Unable to return book");
            return "booksInLibrary";
        }

    }



}