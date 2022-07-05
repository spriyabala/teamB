package com.tw.vapsi.biblioteca.controller;

import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.model.User;
import com.tw.vapsi.biblioteca.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Controller
@Validated
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String createUser(@RequestBody @Valid UserRequest userRequest,Model model) {



       User user =  userService.save(userRequest);
        model.addAttribute("message","User added successfully");

          return "index";
    }

    @GetMapping
    public String fetchUsersOfLibrary(Model model) {

        List<User> listOfUsers = (List<User>) userService.listOfUsers();
        model.addAttribute("users", listOfUsers);
        return "usersLibrary";
    }




}
