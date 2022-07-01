package com.tw.vapsi.biblioteca.repository;
import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.model.CheckedOutBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckedOutBooksRepository extends JpaRepository<CheckedOutBooks, Long> {

    @Query("SELECT a FROM Book a ,CheckedOutBooks b WHERE a.id=b.bookId and b.userName = ?1")
    List<Book> findCheckedOutBook(String userName);

}
