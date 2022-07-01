package com.tw.vapsi.biblioteca.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name ="checkedoutbooks")
public class CheckedOutBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long bookId;
    private String userName;

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckedOutBooks that = (CheckedOutBooks) o;
        return bookId == that.bookId && Objects.equals(id, that.id) && Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId, userName);
    }

    public CheckedOutBooks(long bookId, String userName) {
        this.bookId = bookId;
        this.userName = userName;
    }

    public CheckedOutBooks() {
    }
}
