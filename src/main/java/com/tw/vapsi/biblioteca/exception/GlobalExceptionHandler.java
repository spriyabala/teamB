package com.tw.vapsi.biblioteca.exception;


import com.tw.vapsi.biblioteca.controller.UserForm;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistException.class)
    public String handleEmailAlreadyExistException(EmailAlreadyExistException exception, Model model){

        model.addAttribute("message", "Email Already Exist");
        model.addAttribute("userForm",new UserForm());
        return "registration";
    }

    @ExceptionHandler(UnAuthorizedUserException.class)
    public String handleUnAuthorizedUserException(UnAuthorizedUserException exception, Model model){

        model.addAttribute("errorMessage", "Unauthorized user \n Login to continue");
        return "login";

    }

    @ExceptionHandler(BookNotAvailableException.class)
    public String handleBookNotAvailableException(BookNotAvailableException exception, Model model, HttpServletRequest request){

        String path = request.getPathInfo();
        model.addAttribute("errorMessage", "Book not available in library");
       if(path.contains("return")) {

           return "returnBookSuccess";
       }

        return "booksInLibrary";
    }

    @ExceptionHandler(BookAlreadyReturnedException.class)
    public String handleBookAlreadyReturnedException(BookAlreadyReturnedException exception, Model model){

        model.addAttribute("errorMessage", "Book already returned");
        return "returnBookSuccess";

    }

    @ExceptionHandler(BookAlreadyCheckedOutException.class)
    public String handleBookAlreadyCheckedOutException(BookAlreadyCheckedOutException exception, Model model){

        model.addAttribute("errorMessage", "Book already checked out");
        return "booksInLibrary";

    }
}
