package com.tw.vapsi.biblioteca.controller;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class UserRequest {


    public UserRequest(){

    }

    public  UserRequest(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email  = email;
        this.password = password;
    }
    @NotEmpty(message="firstName cannot be blank or null")
    private String firstName;

    @NotEmpty(message="lastName cannot be blank or null")
    private String lastName;
    private String email;
    @NotEmpty(message="lastName cannot be blank or null")
    private String password;




    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
