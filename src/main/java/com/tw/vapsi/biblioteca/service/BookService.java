package com.tw.vapsi.biblioteca.service;

import com.tw.vapsi.biblioteca.exception.BookNotAvailableException;
import com.tw.vapsi.biblioteca.exception.UnAuthorizedUserException;
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
   public Book saveStaticData(Book book) {
        return bookRepository.save(book);
    }

    public Book save(long id, String name, String author) {
        Book book = new Book(id,name,author);
        return bookRepository.save(book);
    }

    public void issueCheckedOutBook(long id) throws UnAuthorizedUserException, BookNotAvailableException {

        Optional<User> user = userRepository.findByEmail(userService.fetchUserName());

        if(!user.isPresent())
            throw new UnAuthorizedUserException();
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent())
            throw new BookNotAvailableException();
        Book checkedOutBook = book.get();
        List<Book> checkedOutBookList = user.get().getCheckedOutBook();
        checkedOutBookList.add(checkedOutBook);
        user.get().setCheckedOutBook(checkedOutBookList);
        checkedOutBook.setAvailable(false);
        bookRepository.save(checkedOutBook);
        userRepository.save(user.get());

    }

    public List<Book> getCheckedOutBooks() throws UnAuthorizedUserException {

        Optional<User> user = userRepository.findByEmail(userService.fetchUserName());
        if(!user.isPresent())
            throw new UnAuthorizedUserException();
        return user.get().getCheckedOutBook();
    }

    public Book returnBook(Long id) throws UnAuthorizedUserException, BookNotAvailableException {
        Optional<User> user = userRepository.findByEmail(userService.fetchUserName());
        if(!user.isPresent())
            throw new UnAuthorizedUserException();
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent())
            throw new BookNotAvailableException();
        Book checkedOutBook = book.get();
        List<Book> checkedOutBookList = user.get().getCheckedOutBook();
        checkedOutBookList.remove(checkedOutBook);
        user.get().setCheckedOutBook(checkedOutBookList);
        checkedOutBook.setAvailable(true);
        userRepository.save(user.get());
        return bookRepository.save(checkedOutBook);
    }
}