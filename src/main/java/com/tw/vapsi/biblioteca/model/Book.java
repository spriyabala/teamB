package com.tw.vapsi.biblioteca.model;
import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String author;
    private boolean isAvailable=true;


    public Book()
    {
    }
    public Book(String name, String author) {
        this.author = author;
        this.name = name;
    }

    public Book(long id, String name, String author) {
        this.id =id;
        this.author = author;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && Objects.equals(name, book.name) && Objects.equals(author, book.author);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, author);
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean available) {
        isAvailable = available;
    }


}
