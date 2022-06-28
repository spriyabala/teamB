package com.tw.vapsi.biblioteca.controller;


import com.tw.vapsi.biblioteca.controller.helper.ControllerTestHelper;
import com.tw.vapsi.biblioteca.exception.NoBooksInLibraryException;
import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = BooksController.class)
class BooksControllerTest extends ControllerTestHelper {
   /* @Autowired

    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    @Test
    @WithMockUser
    void shouldReturnListOfBooks() throws Exception, NoBooksInLibraryException {
        when(bookService.getList()).thenReturn(List.of(new Book("The River of Adventures","Enid Blyton"), new Book("Muniya","NBT Publications")));


        mockMvc.perform(get("/books"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"name\": \"The River of Adventures\", \"author\": \"Enid Blyton\"}, {\"name\": \"Muniya\", \"author\": \"NBT Publications\"}]"));

        verify(bookService,times(1)).getList();

    }*/

   /* @Test
    void shouldReturn404WhenLibraryHasNoBooks() throws Exception {

        mockMvc.perform(get("/books"))
                //.andExpect(jsonPath("$.length()").value(0))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }*/
}
