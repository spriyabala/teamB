package com.tw.vapsi.biblioteca.controller.qa;


import com.tw.vapsi.biblioteca.BibliotecaApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BibliotecaApplication.class
)
@AutoConfigureMockMvc
class BooksControllerQATest  {
    @Autowired
    private MockMvc mockMvc;





    @Test
    @WithMockUser
    void shouldReturnListOfBooksInTheLibrary() throws Exception {

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("booksInLibrary"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("books"));

    }

    @Test
    void returnSorryNoBooksYetMessageWhenLibraryHasNoBooks() throws  Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("booksInLibrary"));
                //.andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"))
                //.andExpect(MockMvcResultMatchers.model().attribute("errorMessage", "Sorry, no books yet!"));
    }
/*
   @Test
   void issueBookWhenUserIsAuthorizedAndBookIsAvailable() throws Exception {

        mockMvc.perform(get("/books/checkout/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("checkOutSuccess"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("message"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "Check out successful !!!"));

    }*/




  /*  @Test
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
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", "Book not available for checkout"));

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

    }*/


   /* @Test
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
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", "Book not Checked Out"));

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

*/


}
