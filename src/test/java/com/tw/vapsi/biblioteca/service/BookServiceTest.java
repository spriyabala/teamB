package com.tw.vapsi.biblioteca.service;


import com.tw.vapsi.biblioteca.exception.*;
import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.model.User;
import com.tw.vapsi.biblioteca.repository.BookRepository;
import com.tw.vapsi.biblioteca.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest

class   BookServiceTest {
    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private UserRepository userRepository;
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Test
    void shouldGetAllBooksAvailableInTheLibrary()  {
        List<Book> books = new ArrayList<>();
        books.add(new Book("The River of Adventures", "Enid Blyton"));
        books.add(new Book("Muniya", "NBT Publications"));
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> list = bookService.fetchBooksFromLibrary();
        assertEquals(books, list);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void addABookToTheLibrary()  {

        Book book = new Book("The River of Adventures","Enid Blyton");
        book.setId(2L);
        Book bookToBeCheckedOut = new Book("The River of Adventures","Enid Blyton");
        bookToBeCheckedOut.setId(2L);

        when(bookRepository.save(book)).thenReturn(bookToBeCheckedOut);
        Book book1 = bookService.save(2L,"The River of Adventures","Enid Blyton");
        assertEquals(book,book1);
        verify(bookRepository,times(1)).save(bookToBeCheckedOut);


    }
    @Test
    @WithMockUser(username = "1234@gmail.com")
    void issueBookWhenUserIsAuthorizedAndBookIsAvailable() throws Exception{

        Book book = new Book("The River of Adventures","Enid Blyton");
        book.setId(2L);
        book.setAvailable(true);
        Book bookToBeCheckedOut = new Book("The River of Adventures","Enid Blyton");
        bookToBeCheckedOut.setId(2L);
        bookToBeCheckedOut.setAvailable(true);

        User user = new User(1L,
                "User 1 first name",
                "User 1 last name",
                "1234@gmail.com",
                "password");

        when(userRepository.findByEmail("1234@gmail.com")).thenReturn(Optional.of(user));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(bookToBeCheckedOut));
        when(bookRepository.save(book)).thenReturn(bookToBeCheckedOut);
        assertEquals(book,bookService.issueCheckedOutBook(2L));
        verify(bookRepository,times(1)).save(bookToBeCheckedOut);


    }


    @Test
    @WithMockUser(username = "1234@gmail.com")
    void throwsBookNotAvailableExceptionWhenBookIsNotAvailableForCheckOut()  {

        Book book = new Book("The River of Adventures","Enid Blyton");
        book.setId(2L);
        book.setAvailable(false);
        Book bookToBeCheckedOut = new Book("The River of Adventures","Enid Blyton");
        bookToBeCheckedOut.setId(2L);
        bookToBeCheckedOut.setAvailable(false);

        User user = new User(1L,
                "User 1 first name",
                "User 1 last name",
                "1234@gmail.com",
                "password");

        when(userRepository.findByEmail("1234@gmail.com")).thenReturn(Optional.of(user));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(bookToBeCheckedOut));
        when(bookRepository.save(book)).thenReturn(bookToBeCheckedOut);
        assertThrows(BookAlreadyCheckedOutException.class,()->bookService.issueCheckedOutBook(2L));
        verify(userRepository,times(1)).findByEmail("1234@gmail.com");
        verify(bookRepository,times(1)).findById(2L);

    }

    @Test
    @WithMockUser(username = "1234@gmail.com")
    void throwsBookNotAvailableExceptionWhenBookIsNotInLibraryWhenCheckOut() {

        User user = new User(1L,
                "User 1 first name",
                "User 1 last name",
                "1234@gmail.com",
                "password");

        when(userRepository.findByEmail("1234@gmail.com")).thenReturn(Optional.of(user));
        when(bookRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(BookNotAvailableException.class,()->bookService.issueCheckedOutBook(3L));
        verify(userRepository,times(1)).findByEmail("1234@gmail.com");


    }



    @Test
    @WithMockUser(username = "1234@gmail.com")
    void shouldGetAllCheckedOutBooksByUser() throws ServiceException {

        Book bookToBeCheckedOut = new Book("The River of Adventures","Enid Blyton");
        bookToBeCheckedOut.setId(2L);
        bookToBeCheckedOut.setAvailable(false);
        List<Book> bookList = new ArrayList<>();
        bookList.add(bookToBeCheckedOut);

        User user = new User(1L,
                "User 1 first name",
                "User 1 last name",
                "1234@gmail.com",
                "password");
        user.setCheckedOutBook(bookList);

        when(userRepository.findByEmail("1234@gmail.com")).thenReturn(Optional.of(user));

        assertEquals(bookList,bookService.getCheckedOutBooks());
        verify(userRepository,times(1)).findByEmail("1234@gmail.com");

    }


    @Test
    @WithMockUser(username = "1234@gmail.com")
    void throwsUnAuthorizedUserExceptionWhenUserIsNotValidToViewCheckedOutBooks() {

        when(userRepository.findByEmail("1234@gmail.com")).thenReturn(Optional.empty());

        assertThrows(UnAuthorizedUserException.class,()->bookService.getCheckedOutBooks());
    }

    @Test
    @WithMockUser(username = "1234@gmail.com")
    void throwsUnAuthorizedUserExceptionWhenUserIsNotValidWhenCheckOut() {


        when(userRepository.findByEmail("1234@gmail.com")).thenReturn(Optional.empty());

        assertThrows(UnAuthorizedUserException.class,()->bookService.issueCheckedOutBook(2L));
    }


    @Test
    @WithMockUser(username = "1234@gmail.com")
    void throwsUnAuthorizedUserExceptionWhenUserNotAuthorizedToReturn() {

        when(userRepository.findByEmail("email@example.com")).thenReturn(Optional.empty());

        assertThrows(UnAuthorizedUserException.class,()->bookService.returnBook(3L));

    }

    @Test
    @WithMockUser(username = "1234@gmail.com")
    void throwsBookNotAvailableExceptionForReturnWhenBookIsNotInLibrary()  {

        User user = new User(1L,
                "User 1 first name",
                "User 1 last name",
                "1234@gmail.com",
                "password");

        when(userRepository.findByEmail("1234@gmail.com")).thenReturn(Optional.of(user));
        when(bookRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(BookNotAvailableException.class,()->bookService.returnBook(3L));

    }

    @Test
    @WithMockUser(username = "1234@gmail.com")
    void returnBookWhenUserIsAuthorizedAndBookIsAvailable() throws ServiceException {

        Book book = new Book("The River of Adventures","Enid Blyton");
        book.setId(2L);
        book.setAvailable(false);
        Book bookToBeCheckedOut = new Book("The River of Adventures","Enid Blyton");
        bookToBeCheckedOut.setId(2L);
        bookToBeCheckedOut.setAvailable(false);

        User user = new User(1L,
                "User 1 first name",
                "User 1 last name",
                "1234@gmail.com",
                "password");

        when(userRepository.findByEmail("1234@gmail.com")).thenReturn(Optional.of(user));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(bookToBeCheckedOut));
        when(bookRepository.save(book)).thenReturn(bookToBeCheckedOut);
        assertEquals(book,bookService.returnBook(2L));
        verify(bookRepository,times(1)).save(bookToBeCheckedOut);


    }

    @Test
    @WithMockUser(username = "1234@gmail.com")
    void throwsBookAlreadyReturnedExceptionWhenBookIsAlreadyReturned()  {

        Book book = new Book("The River of Adventures","Enid Blyton");
        book.setId(2L);
        book.setAvailable(true);
        Book bookToBeCheckedOut = new Book("The River of Adventures","Enid Blyton");
        bookToBeCheckedOut.setId(2L);
        bookToBeCheckedOut.setAvailable(true);

        User user = new User(1L,
                "User 1 first name",
                "User 1 last name",
                "1234@gmail.com",
                "password");

        when(userRepository.findByEmail("1234@gmail.com")).thenReturn(Optional.of(user));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(bookToBeCheckedOut));
        when(bookRepository.save(book)).thenReturn(bookToBeCheckedOut);
        assertThrows(BookAlreadyReturnedException.class,()->bookService.returnBook(2L));
        verify(userRepository,times(1)).findByEmail("1234@gmail.com");
        verify(bookRepository,times(1)).findById(2L);

    }


}