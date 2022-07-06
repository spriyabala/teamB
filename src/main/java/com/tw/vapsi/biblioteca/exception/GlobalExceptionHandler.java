package com.tw.vapsi.biblioteca.exception;


import com.tw.vapsi.biblioteca.controller.UserForm;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistException.class)
    public String handleEmailAlreadyExistException(EmailAlreadyExistException exception, Model model){

        model.addAttribute("message", "Email Already Exist");
        model.addAttribute("userForm",new UserForm());
        return "registration";
    }
}
