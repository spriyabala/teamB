package com.tw.vapsi.biblioteca.controller;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserForm {


    @NotEmpty(message = "First Name can not be blank")
    private String firstName;

    @NotEmpty(message = "Last Name can not be blank")
    private String lastName;

    @NotEmpty(message = "Email can not be blank")
    @Email(regexp = "^(.+)@(.+)$", message = "Invalid email")
    private String email;

    @NotEmpty(message="Password can not be blank")
    private String password;




    public UserForm( String firstName, String lastName, String email, String password) {


        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }

    public UserForm() {

    }





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
