package com.tw.vapsi.biblioteca.service;

import com.tw.vapsi.biblioteca.exception.BookNotAvailableException;
import com.tw.vapsi.biblioteca.exception.BookNotReturnedException;
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
public class ReturnBookService {
    @Autowired
    private CheckedOutBooksRepository checkedOutBooksRepository;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    public CheckedOutBooks removeACheckedOutBook(Long id) throws UnAuthorizedUserException, BookNotReturnedException {
            UserServiceHelper userServiceHelper = new UserServiceHelper();
        CheckedOutBooks checkedOutBooks = new CheckedOutBooks(id, userServiceHelper.fetchUserName());
        int rowsDeleted=checkedOutBooksRepository.deleteCheckedOutBookByBookId(id);
        if(rowsDeleted==0)
            throw new BookNotReturnedException();

        return checkedOutBooks;

    }

    public void checkUserAccess() throws UnAuthorizedUserException {
        UserServiceHelper userServiceHelper = new UserServiceHelper();
        if (!(userServiceHelper.userRole().contains(new SimpleGrantedAuthority("ROLE_USER")) ||
                userServiceHelper.userRole().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))))
            throw new UnAuthorizedUserException();


    }

    public CheckedOutBooks bookPresentInCheckedOutBooksList(long id) throws BookNotAvailableException {

        if (checkedOutBooksRepository.findById(id) == null)
            throw new BookNotAvailableException();

        return checkedOutBooksRepository.findById(id).get();


    }


    public void updateAvailableFlag(Book book) {

        book.setAvailable(true);
        bookRepository.save(book);
    }

    public List<CheckedOutBooks> getCheckOutBookList() {
        return checkedOutBooksRepository.findAll();
    }
}

