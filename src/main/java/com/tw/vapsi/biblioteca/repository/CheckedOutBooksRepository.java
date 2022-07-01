package com.tw.vapsi.biblioteca.repository;
import com.tw.vapsi.biblioteca.model.Book;
import com.tw.vapsi.biblioteca.model.CheckedOutBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckedOutBooksRepository extends JpaRepository<CheckedOutBooks, Long> {

}
