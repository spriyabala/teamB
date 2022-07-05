package com.tw.vapsi.biblioteca.controller.qa;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.vapsi.biblioteca.controller.UserController;
import com.tw.vapsi.biblioteca.controller.UserRequest;
import com.tw.vapsi.biblioteca.controller.helper.ControllerTestHelper;
import com.tw.vapsi.biblioteca.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserController.class)
public class UserControllerQATest extends ControllerTestHelper {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String email;
    private String firstName;
    private String lastName;
    private String password;

    @BeforeEach
    void setUp() {
        email = "test-mail@test.com";
        firstName = "Micky";
        lastName = "Mouse";
        password = "password@123";
    }

    @Test
    public void registerACustomerWithFirstNameLastNamePasswordAndEmail() throws Exception {
        User returnedUser = new User(1L, firstName, lastName, email, password);

        UserRequest userRequest = new UserRequest(firstName, lastName, email, password);


        String jsonRequest = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/users")

                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("message"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "User added successfully"));

    }

    public void RegisterALibraryAdminWithEmailFirstNameLastNamePasswordAndEmail(){

    }
    public void RegisterACustomerWithInvalidEmailAddressGivesHTTPResponse200WithErrorMessageInValidEmail(){

    }

    public void RegisterALibraryAdminWithInvalidEmailAddressGivesHTTPResponse200WithErrorMessageInValidEmail(){

    }

    public void RegisterUserWithBlankDataGivesHTTPResponse200WithErrorMessageFiedsCannotBeNull(){

    }



}