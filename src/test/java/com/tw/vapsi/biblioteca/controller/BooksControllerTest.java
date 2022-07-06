package com.tw.vapsi.biblioteca.controller;


import com.tw.vapsi.biblioteca.controller.helper.ControllerTestHelper;
import com.tw.vapsi.biblioteca.exception.UnAuthorizedUserException;
import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    @Autowired

    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;



    @Test
    @WithMockUser
    void shouldReturnListOfBooks() throws Exception {
        Book book1 = new Book("The River of Adventures", "Enid Blyton");
        Book book2 = new Book("Muniya", "NBT Publications");

        List<Book> list = new ArrayList<Book>();
        list.add(book1);
        list.add(book2);
        when(bookService.fetchBooksFromLibrary()).thenReturn(list);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("booksInLibrary"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("books"))
                .andExpect(MockMvcResultMatchers.model().attribute("books", list));

        verify(bookService, times(1)).fetchBooksFromLibrary();

    }

    @Test
    void shouldDisplayErrorMessageWhenLibraryHasNoBooks() throws Exception, UnAuthorizedUserException {
        List<Book> books = new ArrayList<Book>();
        when(bookService.fetchBooksFromLibrary()).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("booksInLibrary"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", "Sorry, no books yet!"));
       // verify(bookService, times(1));

        verify(bookService, times(1)).fetchBooksFromLibrary();


    }

    //-------------

   /* @Test
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

*/


}
