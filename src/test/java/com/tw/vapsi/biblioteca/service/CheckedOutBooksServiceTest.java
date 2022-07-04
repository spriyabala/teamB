package com.tw.vapsi.biblioteca.service;


import com.tw.vapsi.biblioteca.exception.BookNotAvailableException;
import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.model.CheckedOutBooks;
import com.tw.vapsi.biblioteca.repository.BookRepository;
import com.tw.vapsi.biblioteca.repository.CheckedOutBooksRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CheckedOutBooksServiceTest {


    @MockBean
    private CheckedOutBooksService checkedOutBooksService;

    @MockBean
    private CheckedOutBooksRepository checkedOutBooksRepository;

    @Autowired
    private BookRepository bookRepository;


    @Test
    void shouldGetListOfBooks() {

        CheckedOutBooks checkedOutBooks = new CheckedOutBooks(2L,"vaishnnavims23@gmail.com");
        checkedOutBooks.setId(2L);
        CheckedOutBooks booksToBeCheckedOut = new CheckedOutBooks(2L,"vaishnnavims23@gmail.com");
        booksToBeCheckedOut.setId(2L);
        when(checkedOutBooksRepository.save(booksToBeCheckedOut)).thenReturn(checkedOutBooks);

        assertEquals(checkedOutBooks,checkedOutBooksRepository.save(booksToBeCheckedOut));
        verify(checkedOutBooksRepository,times(1)).save(booksToBeCheckedOut);

    }

   /* @Test
    void shouldThrowExceptionWhenBookNotPresentInTheMainBookTable()  {
        Book book = new Book("The River of Adventures", "Enid Blyton");
        book.setId(1L);

        when(bookRepository.save(any())).thenReturn(book);

        assertThrows(BookNotAvailableException.class,()->checkedOutBooksService.bookAvailableInLibrary( 11L));
        verify(bookRepository,times(1)).save(book);

    }*/

    @Test
    void shouldReturnTrueWhenBookIsPresentInTheMainBookTable() throws Exception {

        Book book = new Book("The River of Adventures", "Enid Blyton");
        book.setId(2L);
        CheckedOutBooks booksToBeCheckedOut = new CheckedOutBooks(2L,"vaishnnavims23@gmail.com");
        booksToBeCheckedOut.setId(2L);
        when(checkedOutBooksService.bookAvailableInLibrary(2L)).thenReturn(book);

        assertEquals(book,checkedOutBooksService.bookAvailableInLibrary(2L));
        verify(checkedOutBooksService,times(1)).bookAvailableInLibrary(2L);


    }

   /* @Test
    void shouldThrowExceptionWhenBookIsPresentInTheMainBookTableAndNotAvailableForCheckout() throws Exception {
        Book book = new Book("The River of Adventures", "Enid Blyton");
        book.setId(1L);
        book.setAvailable(false);
        bookRepository.save(book);
        assertThrows(BookNotAvailableException.class,()->checkedOutBooksService.bookAvailableInLibrary( 1L));

    }*/

    @Test
    void shouldReturnTrueWhenBookIsPresentInTheMainBookTableAndNotAvailableForCheckout() throws Exception {
        {

            Book book = new Book("The River of Adventures", "Enid Blyton");
            book.setId(2L);
            book.setAvailable(true);
            CheckedOutBooks booksToBeCheckedOut = new CheckedOutBooks(2L,"vaishnnavims23@gmail.com");
            booksToBeCheckedOut.setId(2L);
            when(checkedOutBooksService.bookAvailableInLibrary(2L)).thenReturn(book);

            assertEquals(book,checkedOutBooksService.bookAvailableInLibrary(2L));
            verify(checkedOutBooksService,times(1)).bookAvailableInLibrary(2L);


        }

    }




}