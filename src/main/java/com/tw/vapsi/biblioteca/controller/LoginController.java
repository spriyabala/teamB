package com.tw.vapsi.biblioteca.controller;

import com.tw.vapsi.biblioteca.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class LoginController {
    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/users/registration")
    public String signup() {
        return "registration";
    }
    @PostMapping("/users/registration")
    public String registerUser(){
        return "registration";
    }


}
