package com.tw.vapsi.biblioteca.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.vapsi.biblioteca.controller.helper.ControllerTestHelper;
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

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        email = "test-mail@test.com";
        firstName = "Micky";
        lastName = "Mouse";
        password = "password@123";
    }

    @Test
    void registerCustomerWithFirstNameLastNameEmailAndPassword() throws Exception {
        User returnedUser = new User(1L, firstName, lastName, email, password);

        UserRequest userRequest = new UserRequest(firstName, lastName, email, password);
        when(userService.save(userRequest))
                .thenReturn(returnedUser);

        String jsonRequest = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/users")

                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("message"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "User added successfully"));

        verify(userService, never()).save(userRequest);

    }

    @Test
    void shouldNotCreateUserWhenFirstNameIsMissing() throws Exception {

        mockMvc.perform(post("/users")

                        .param("lastName", lastName)
                        .param("email", email)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status()
                        .reason(createReasonFor("firstName"))
                );
        //verify(userService, never()).save(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void shouldNotCreateUserWhenLastNameIsMissing() throws Exception {

        mockMvc.perform(post("/users")
                        .param("firstName", firstName)
                        .param("email", email)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status()
                        .reason(createReasonFor("lastName"))
                );
       // verify(userService, never()).save(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void shouldNotCreateUserWhenEmailIsMissing() throws Exception {

        mockMvc.perform(post("/users")
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status()
                        .reason(createReasonFor("email"))
                );
        //verify(userService, never()).save(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void shouldNotCreateUserWhenPasswordIsMissing() throws Exception {

        mockMvc.perform(post("/users")
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status()
                        .reason(createReasonFor("password"))
                );
       // verify(userService, never()).save(any());
    }

    private String createReasonFor(String parameterName) {
        return "Required request parameter '" + parameterName + "' for method parameter type String is not present";
    }
}