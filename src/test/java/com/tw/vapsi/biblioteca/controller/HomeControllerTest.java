package com.tw.vapsi.biblioteca.controller;


import com.tw.vapsi.biblioteca.controller.helper.ControllerTestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HomeController.class)
class HomeControllerTest extends ControllerTestHelper {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldShowWelcomeMessage() throws Exception {
         mockMvc.perform(get("/biblioteca").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    void shouldShowMainMenu() throws Exception {
        mockMvc.perform(get("/mainMenu").contentType("application/json"))
                .andExpect(status().is3xxRedirection());

    }
}
