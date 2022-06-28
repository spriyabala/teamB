package com.tw.vapsi.biblioteca.controller;


import com.tw.vapsi.biblioteca.controller.helper.ControllerTestHelper;
import com.tw.vapsi.biblioteca.exception.NoBooksInLibraryException;
import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.service.BookService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = BooksController.class)
class BooksControllerTest extends ControllerTestHelper {
<<<<<<< Updated upstream
   @Autowired
=======
    @Autowired
>>>>>>> Stashed changes

    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;

    @Test
    @WithMockUser
    void shouldReturnListOfBooks() throws Exception, NoBooksInLibraryException {
                Book book1 = new Book("The River of Adventures", "Enid Blyton");
            Book book2 = new Book("Muniya", "NBT Publications");

        List<Book> list = new ArrayList<Book>();
        list.add(book1);
        list.add(book2);
                when(bookService.getList()).thenReturn(list);


        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("booklist"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("books"));
              // .andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.arrayContaining(list.toArray())));


<<<<<<< Updated upstream
    }

     @Test
  void shouldReturn404WhenLibraryHasNoBooks() throws Exception, NoBooksInLibraryException {
=======

        verify(bookService, times(1)).getList();

    }

    @Test
    void shouldReturn404WhenLibraryHasNoBooks() throws Exception, NoBooksInLibraryException {
>>>>>>> Stashed changes
        when(bookService.getList()).thenReturn(null);

        mockMvc.perform(get("/books"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));


    }
}

