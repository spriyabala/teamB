package com.tw.vapsi.biblioteca.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @GetMapping("/Biblioteca")
    public String Biblioteca()
    {
        return "Index";
    }

}
