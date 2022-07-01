package com.tw.vapsi.biblioteca.controller;

import com.tw.vapsi.biblioteca.exception.UnAuthorizedUserException;

import com.tw.vapsi.biblioteca.service.CheckedOutBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/checkout")
public class CheckedOutBooksController {
    @Autowired
    private CheckedOutBooksService checkedOutBooksService;




    @GetMapping("/{id}")
    public String checkedOutBook(@PathVariable("id") long id,Model model)  {
        try {

            checkedOutBooksService.saveCheckoutDetails(id);
            model.addAttribute("message", "Check out successful !!!");
            return "checkoutsuccess";
        }
        catch(UnAuthorizedUserException e)
        {
            model.addAttribute("errorMessage", "Unauthorized user \n Login to continue");

            return "login";
        }

    }

}