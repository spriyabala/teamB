package com.tw.vapsi.biblioteca.service;

import com.tw.vapsi.biblioteca.exception.*;
import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.model.User;
import com.tw.vapsi.biblioteca.repository.BookRepository;
import com.tw.vapsi.biblioteca.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    public List<Book> fetchBooksFromLibrary() {
        return bookRepository.findAll();
    }


    public Book save(long id, String name, String author) {
        Book book = new Book(id, name, author);
        return bookRepository.save(book);
    }


    public Book issueCheckedOutBook(long id) throws ServiceException {

        Optional<User> user = userRepository.findByEmail(userService.fetchUserName());
        isAuthorizedUser(user);
        Optional<Book> book = bookRepository.findById(id);
        isBookAvailableInTheLibrary(book);
        Book checkedOutBook = book.get();
        if (!checkedOutBook.isAvailable())
            throw new BookAlreadyCheckedOutException();
        List<Book> checkedOutBookList = user.get().getCheckedOutBook();
        checkedOutBookList.add(checkedOutBook);
        user.get().setCheckedOutBook(checkedOutBookList);
        checkedOutBook.setAvailable(false);
        userRepository.save(user.get());
        return bookRepository.save(checkedOutBook);

    }

    public List<Book> getCheckedOutBooks() throws ServiceException {

        Optional<User> user = userRepository.findByEmail(userService.fetchUserName());
        if (!user.isPresent())
            throw new UnAuthorizedUserException();
        return user.get().getCheckedOutBook();
    }

    public Book returnBook(Long id) throws ServiceException {

        Optional<User> user = userRepository.findByEmail(userService.fetchUserName());
        isAuthorizedUser(user);
        Optional<Book> book = bookRepository.findById(id);
        isBookAvailableInTheLibrary(book);
        Book checkedOutBook = book.get();
        if (checkedOutBook.isAvailable())
            throw new BookAlreadyReturnedException();
        List<Book> checkedOutBookList = user.get().getCheckedOutBook();
        checkedOutBookList.remove(checkedOutBook);
        user.get().setCheckedOutBook(checkedOutBookList);
        checkedOutBook.setAvailable(true);
        userRepository.save(user.get());
        return bookRepository.save(checkedOutBook);
    }

    private void isAuthorizedUser(Optional<User> user) throws UnAuthorizedUserException {
        if (!user.isPresent())
            throw new UnAuthorizedUserException();
    }

    private void isBookAvailableInTheLibrary(Optional<Book> book) throws BookNotAvailableException {
        if (!book.isPresent())
            throw new BookNotAvailableException();
    }
}