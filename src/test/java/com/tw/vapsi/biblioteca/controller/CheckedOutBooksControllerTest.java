package com.tw.vapsi.biblioteca.controller;

import com.tw.vapsi.biblioteca.controller.helper.ControllerTestHelper;
import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.model.CheckedOutBooks;
import com.tw.vapsi.biblioteca.repository.BookRepository;
import com.tw.vapsi.biblioteca.repository.CheckedOutBooksRepository;
import com.tw.vapsi.biblioteca.service.CheckedOutBooksService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CheckedOutBooksController.class)
class CheckedOutBooksControllerTest extends ControllerTestHelper {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CheckedOutBooksService checkedOutBooksService;



    @Test
    void shouldSaveCheckedOutDetails() throws Exception {
        CheckedOutBooks checkedOutBooks = new CheckedOutBooks(2,"abc@gmail.com");
        CheckedOutBooks toBeCheckedOut =  new CheckedOutBooks(2,"abc@gmail.com");
        toBeCheckedOut.setId(2L);
        when(checkedOutBooksService.saveCheckoutDetails(2L)).thenReturn(checkedOutBooks);

        mockMvc.perform(get("/checkout/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("checkoutsuccess"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("message"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "Check out successful !!!"));

        verify(checkedOutBooksService, times(1)).saveCheckoutDetails(2L);

    }

    @Test
    void shouldReturnTrueWhenBookIsAvailableForCheckOut() throws Exception {
        Book book1 = new Book("The River of Adventures", "Enid Blyton");
        Book book2 = new Book("The River of Adventures", "Enid Blyton");
        book1.setId(2);
        when(checkedOutBooksService.bookAvailableInLibrary(2L)).thenReturn(book2);

        mockMvc.perform(get("/checkout/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("checkoutsuccess"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("message"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "Check out successful !!!"));

        verify(checkedOutBooksService, times(1)).bookAvailableInLibrary(2L);

    }



}