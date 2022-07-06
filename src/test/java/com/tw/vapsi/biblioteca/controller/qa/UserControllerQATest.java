package com.tw.vapsi.biblioteca.controller.qa;


import com.tw.vapsi.biblioteca.BibliotecaApplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BibliotecaApplication.class
)
@AutoConfigureMockMvc
public class UserControllerQATest  {


    @Autowired
    private MockMvc mockMvc;



    private String email;
    private String firstName;
    private String lastName;
    private String password;

    public static final String FORM_OBJECT_ALIAS = "userForm";
    public static final String FORM_FIELD_NAME_TITLE = "firstName";

    public static final String VALIDATION_ERROR_NOT_EMPTY = "NotEmpty";

    public static final String FORM_FIELD_NAME_LAST_NAME= "lastName";

    public static final String FORM_FIELD_NAME_EMAIL = "email";

    private static final String VALIDATION_ERROR_INVALID_EMAIL = "Email";

    private static final String FORM_FIELD_NAME_PASSWORD = "password";

    @BeforeEach
    void setUp() {
        email = "test-mail@test.com";
        firstName = "Micky";
        lastName = "Mouse";
        password = "password@123";
    }

    @Test
    public void registerACustomerWithFirstNameLastNamePasswordAndEmail() throws Exception {
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

    }

    @Test
    public void registrationFailsWithErrorConstrainNotBlankWhenFirstNameMissing() throws Exception {

        mockMvc.perform(post("/users")
                        .param("lastName", lastName)
                        .param("email", email)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode(
                                FORM_OBJECT_ALIAS,
                                FORM_FIELD_NAME_TITLE,
                                VALIDATION_ERROR_NOT_EMPTY
                        )
                );

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

    }
    @Test
    void registrationFailsWithErrorConstrainEmailWhenInvalidEmailGiven() throws Exception {

        mockMvc.perform(post("/users")
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .param("password", password)
                        .param("email", "abcd")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode(
                                FORM_OBJECT_ALIAS,
                                FORM_FIELD_NAME_EMAIL,
                                VALIDATION_ERROR_INVALID_EMAIL
                        )
                );

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

    }


    @Test
    void registrationFailsIfEmailAlreadyExist() throws Exception {

        mockMvc.perform(post("/users")
                        .param("firstName", "teresa")
                        .param("lastName", "lname")
                        .param("email", "test@gmail.com")
                        .param("password", "abcd")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(post("/users")
                        .param("firstName", "fname")
                        .param("lastName", "lname")
                        .param("email", "test@gmail.com")
                        .param("password", "abcd123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("registration"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("message"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "Email Already Exist"));

    }

}