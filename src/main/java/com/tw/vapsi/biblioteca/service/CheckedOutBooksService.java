package com.tw.vapsi.biblioteca.service;

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
    private UserDetailsService userDetailsService;



    public CheckedOutBooks saveCheckoutDetails(Long id) throws UnAuthorizedUserException {
        UserServiceHelper userServiceHelper = new UserServiceHelper();
        CheckedOutBooks checkedOutBooks = null;
        if(!(userServiceHelper.userRole().contains( new SimpleGrantedAuthority("ROLE_USER"))||
                userServiceHelper.userRole().contains( new SimpleGrantedAuthority("ROLE_LIBRARIAN"))))
            throw new UnAuthorizedUserException();

        checkedOutBooks = new CheckedOutBooks(id,userServiceHelper.fetchUserName());
        checkedOutBooksRepository.save(checkedOutBooks);
        return checkedOutBooks;

    }

}