package com.tw.vapsi.biblioteca.controller;

import com.tw.vapsi.biblioteca.controller.helper.ControllerTestHelper;
import com.tw.vapsi.biblioteca.exception.EmailAlreadyExistException;
import com.tw.vapsi.biblioteca.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest extends ControllerTestHelper {
    @Autowired
    private MockMvc mockMvc;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private static final String FORM_OBJECT_ALIAS = "userForm";
    private static final String FORM_FIELD_NAME_FIRST_NAME = "firstName";

    private static final String FORM_FIELD_NAME_LAST_NAME= "lastName";

    private static final String VALIDATION_ERROR_NOT_EMPTY = "NotEmpty";
    private static final String VALIDATION_ERROR_INVALID_EMAIL = "Email";

    private static final String FORM_FIELD_NAME_PASSWORD = "password";

    private static final String FORM_FIELD_NAME_EMAIL = "email";

    @BeforeEach
    void setUp() {
        email = "test-mail@test.com";
        firstName = "Micky";
        lastName = "Mouse";
        password = "password@123";
    }

    @Test
    void registerACustomerWithFirstNameLastNamePasswordAndEmail() throws Exception {
        User user = new User(1L, firstName, lastName, email, password);
        when(userService.save(firstName, lastName, email, password))
                .thenReturn(user);

        mockMvc.perform(post("/users")
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .param("email", email)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("message"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "Registration Successful..."));
        verify(userService, times(1)).save(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void registrationFailsWithErrorConstrainNotBlankWhenFirstNameMissing() throws Exception {

        mockMvc.perform(post("/users")
                        .param("lastName", lastName)
                        .param("email", email)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode(
                        FORM_OBJECT_ALIAS,
                                FORM_FIELD_NAME_FIRST_NAME,
                                VALIDATION_ERROR_NOT_EMPTY
                )
                );
        verify(userService, never()).save(anyString(), anyString(), anyString(), anyString());
    }


    @Test
    void registrationFailsWithErrorConstrainNotBlankWhenLastNameMissing() throws Exception {

        mockMvc.perform(post("/users")
                        .param("firstName", firstName)
                        .param("email", email)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode(
                                FORM_OBJECT_ALIAS,
                        FORM_FIELD_NAME_LAST_NAME,
                                VALIDATION_ERROR_NOT_EMPTY
                        )
                );
        verify(userService, never()).save(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void registrationFailsWithErrorConstrainNotBlankWhenEmailMissing() throws Exception {

        mockMvc.perform(post("/users")
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode(
                                FORM_OBJECT_ALIAS,
                                FORM_FIELD_NAME_EMAIL,
                                VALIDATION_ERROR_NOT_EMPTY
                        )
                );
        verify(userService, never()).save(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void registrationFailsWithErrorConstrainEmailWhenInvalidEmailGiven() throws Exception {

        mockMvc.perform(post("/users")
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .param("password", password)
                        .param("email", "email")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode(
                                FORM_OBJECT_ALIAS,
                                FORM_FIELD_NAME_EMAIL,
                        VALIDATION_ERROR_INVALID_EMAIL
                        )
                );
        verify(userService, never()).save(anyString(), anyString(), anyString(), anyString());
    }
    @Test
    void registrationFailsWithErrorConstrainNotBlankWhenPasswordMissing() throws Exception {

        mockMvc.perform(post("/users")
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode(
                                FORM_OBJECT_ALIAS,
                                FORM_FIELD_NAME_PASSWORD,
                                VALIDATION_ERROR_NOT_EMPTY
                        )
                );
        verify(userService, never()).save(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void registrationFailsIfEmailAlreadyExist() throws Exception {

        mockMvc.perform(post("/users")
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .param("email", email)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        when(userService.save(firstName, lastName, email, password))
                .thenThrow(EmailAlreadyExistException.class);
        mockMvc.perform(post("/users")
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .param("email", email)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("registration"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("message"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "Email Already Exist"));

    }

}