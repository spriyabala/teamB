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
import java.util.Optional;

@Service
public class CheckedOutBooksService {

    @Autowired
    CheckedOutBooksRepository checkedOutBooksRepository;

    @Autowired
    BookRepository bookRepository;


    public CheckedOutBooks saveCheckoutDetails(Long id) {
        UserServiceHelper userServiceHelper = new UserServiceHelper();
        CheckedOutBooks checkedOutBooks = new CheckedOutBooks(id, userServiceHelper.fetchUserName());
        return checkedOutBooksRepository.save(checkedOutBooks);

    }

    public void checkUserAccess() throws UnAuthorizedUserException {
        UserServiceHelper userServiceHelper = new UserServiceHelper();
        if (!(userServiceHelper.userRole().contains(new SimpleGrantedAuthority("ROLE_USER")) ||
                userServiceHelper.userRole().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))))
            throw new UnAuthorizedUserException();
    }

    public Book bookAvailableInLibrary(long id) throws BookNotAvailableException {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent() || !book.get().isAvailable())
            throw new BookNotAvailableException();

        return book.get();

    }

    public void updateAvailableFlag(Book book) {
        book.setAvailable(false);
        bookRepository.save(book);
    }

    public List<Book> checkedOutBookDetails() {
        UserServiceHelper userServiceHelper = new UserServiceHelper();
        return checkedOutBooksRepository.findCheckedOutBook
                (userServiceHelper.fetchUserName());
    }
    public List<CheckedOutBooks> getCheckOutBookList() {
        return checkedOutBooksRepository.findAll();
    }


}



