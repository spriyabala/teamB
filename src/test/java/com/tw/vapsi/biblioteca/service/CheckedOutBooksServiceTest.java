package com.tw.vapsi.biblioteca.service;


import com.tw.vapsi.biblioteca.model.CheckedOutBooks;
import com.tw.vapsi.biblioteca.repository.CheckedOutBooksRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class CheckedOutBooksServiceTest {


    @Autowired
    private CheckedOutBooksService checkedOutBooksService;

    @Autowired
    private CheckedOutBooksRepository checkedOutBooksRepository;


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

}