package com.tw.vapsi.biblioteca.controller;


import com.tw.vapsi.biblioteca.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.Errors;

import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String createUser(@Valid UserForm userForm, Errors errors, Model model) throws Exception {
        if (errors.hasErrors()) {

            return "registration";
        }

        model.addAttribute("message", "Registration Successful...");

        userService.save(userForm.getFirstName(), userForm.getLastName(), userForm.getEmail(), userForm.getPassword());

        return "index";
    }

    @GetMapping
    public String signup(UserForm userForm) {
        return "registration";
    }

}
