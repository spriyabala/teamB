package com.tw.vapsi.biblioteca.service;

import com.tw.vapsi.biblioteca.exception.BookNotAvailableException;
import com.tw.vapsi.biblioteca.exception.UnAuthorizedUserException;
import com.tw.vapsi.biblioteca.helper.UserServiceHelper;
import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.model.CheckedOutBooks;
import com.tw.vapsi.biblioteca.repository.BookRepository;
import com.tw.vapsi.biblioteca.repository.CheckedOutBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckedOutBooksService {

    @Autowired
    private CheckedOutBooksRepository checkedOutBooksRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    public CheckedOutBooks saveCheckoutDetails(Long id) throws UnAuthorizedUserException {
        UserServiceHelper userServiceHelper = new UserServiceHelper();
        CheckedOutBooks checkedOutBooks = new CheckedOutBooks(id,userServiceHelper.fetchUserName());
        checkedOutBooksRepository.save(checkedOutBooks);
        return checkedOutBooks;

    }

    public void checkUserAccess() throws UnAuthorizedUserException {
        UserServiceHelper userServiceHelper = new UserServiceHelper();
        if(!(userServiceHelper.userRole().contains( new SimpleGrantedAuthority("ROLE_USER"))||
                userServiceHelper.userRole().contains( new SimpleGrantedAuthority("ROLE_LIBRARIAN"))))
            throw new UnAuthorizedUserException();


    }

    public Book bookAvailable(long id) throws BookNotAvailableException {

        if(bookRepository.findById(id)==null)
            throw new BookNotAvailableException();

       return bookRepository.findById(id).get();



    }


    public void updateAvailableFlag(Book book) {
        book.setAvailable(false);
        bookRepository.save(book);
    }
}