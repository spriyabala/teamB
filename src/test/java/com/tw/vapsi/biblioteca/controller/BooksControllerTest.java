package com.tw.vapsi.biblioteca.controller;


import com.tw.vapsi.biblioteca.controller.helper.ControllerTestHelper;
import com.tw.vapsi.biblioteca.exception.BookAlreadyCheckedOutException;
import com.tw.vapsi.biblioteca.exception.BookAlreadyReturnedException;
import com.tw.vapsi.biblioteca.exception.BookNotAvailableException;
import com.tw.vapsi.biblioteca.exception.UnAuthorizedUserException;
import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.repository.BookRepository;
import com.tw.vapsi.biblioteca.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = BooksController.class)
class BooksControllerTest extends ControllerTestHelper {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;



    @Test
    @WithMockUser
    void shouldReturnListOfBooksInTheLibrary() throws Exception {
        Book book1 = new Book("The River of Adventures", "Enid Blyton");
        Book book2 = new Book("Muniya", "NBT Publications");

        List<Book> list = new ArrayList<>();
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
    void returnSorryNoBooksYetMessageWhenLibraryHasNoBooks() throws  Exception {
        List<Book> books = new ArrayList<>();
        when(bookService.fetchBooksFromLibrary()).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("booksInLibrary"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", "Sorry, no books yet!"));

        verify(bookService, times(1)).fetchBooksFromLibrary();


    }

    @Test
    void issueBookWhenUserIsAuthorizedAndBookIsAvailable() throws Exception {

        Book book = new Book(2L,"The River of Adventures", "Enid Blyton");

        when(bookService.issueCheckedOutBook(2L)).thenReturn(book);

        mockMvc.perform(get("/books/checkout/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("checkOutSuccess"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("message"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "Check out successful !!!"));

        verify(bookService, times(1)).issueCheckedOutBook(2L);


    }

    @Test
    void failedCheckoutWhenBookIsAlreadyCheckedOut() throws Exception {
        when(bookService.issueCheckedOutBook(2L)).thenThrow(BookAlreadyCheckedOutException.class);

        mockMvc.perform(get("/books/checkout/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("booksInLibrary"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", "Book already checked out"));

    }

    @Test
    void failedCheckoutForUnauthorizedUser() throws Exception {

        when(userService.fetchUserName()).thenReturn(null);
        when(bookService.issueCheckedOutBook(2L)).thenThrow(UnAuthorizedUserException.class);
        mockMvc.perform(get("/books/checkout/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", "Unauthorized user \n Login to continue"));


    }

    @Test
    void failedCheckoutWhenBookIsNotAvailable() throws Exception {

        when(userService.fetchUserName()).thenReturn("1234@gmail.com");
        when(bookRepository.findById(3L)).thenReturn(Optional.empty());
        when(bookService.issueCheckedOutBook(2L)).thenThrow(BookNotAvailableException.class);
        mockMvc.perform(get("/books/checkout/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("booksInLibrary"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", "This Book doesn’t belong to this library"));

    }






    @Test
    void getAllCheckedOutBooksByUser() throws Exception {

        Book book = new Book(2L,"The River of Adventures", "Enid Blyton");
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        when(bookService.getCheckedOutBooks()).thenReturn(bookList);

        mockMvc.perform(get("/books/checkout"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("checkOutBooks"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("books"))
                .andExpect(MockMvcResultMatchers.model().attribute("books", bookList));

        verify(bookService, times(1)).getCheckedOutBooks();


    }

    @Test
    void failedToViewCheckoutForUnauthorizedUser() throws Exception {

        when(userService.fetchUserName()).thenReturn(null);
        when(bookService.getCheckedOutBooks()).thenThrow(UnAuthorizedUserException.class);
        mockMvc.perform(get("/books/checkout"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", "Unauthorized user \n Login to continue"));

    }


    @Test
    void failedReturnForUnauthorizedUser() throws Exception {

        when(userService.fetchUserName()).thenReturn(null);
        when(bookService.returnBook(2L)).thenThrow(UnAuthorizedUserException.class);
        mockMvc.perform(get("/books/return/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", "Unauthorized user \n Login to continue"));

    }

    @Test
    void failedReturnWhenBookIsNotAvailable() throws Exception {

        when(userService.fetchUserName()).thenReturn("1234@gmail.com");
        when(bookRepository.findById(3L)).thenReturn(Optional.empty());
        when(bookService.returnBook(2L)).thenThrow(BookNotAvailableException.class);
        mockMvc.perform(get("/books/return/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("returnBookSuccess"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", "This Book doesn’t belong to this library"));

    }


    @Test
    void returnBookWhenUserIsAuthorizedAndBookIsInLibrary() throws Exception {

        Book book = new Book(2L,"The River of Adventures", "Enid Blyton");

        when(bookService.returnBook(2L)).thenReturn(book);

        mockMvc.perform(get("/books/return/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("returnBookSuccess"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("message"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "Returned Book successful !!!"));

        verify(bookService, times(1)).returnBook(2L);


    }

    @Test
    void failedReturnWhenBookIsAlreadyReturned() throws Exception {
        when(bookService.returnBook(2L)).thenThrow(BookAlreadyReturnedException.class);

        mockMvc.perform(get("/books/return/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("returnBookSuccess"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", "Book already returned"));

    }

    @Test
    void shouldCreateABook() throws Exception {

        Book book = new Book(2L,"The River of Adventures", "Enid Blyton");


        mockMvc.perform(post("/books").param("id","2")
                        .param("name","The River of Adventures").param("author","Enid Blyton"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).save(2,"The River of Adventures","Enid Blyton");

    }












}
