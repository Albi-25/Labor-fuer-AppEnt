package com.example.books;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GreetingController {

    public static class Book {
        private long id;
        private String author;
        private String title;

        public Book(long id, String author, String title) {
            this.id = id;
            this.author = author;
            this.title = title;
        }

        public long getId() {
            return id;
        }

        public String getAuthor() {
            return author;
        }

        public String getTitle() {
            return title;
        }
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Martin Kleppmann", "Designing Data-Intensive Applications"));
        books.add(new Book(2, "Robert C. Martin", "Clean Code"));
        books.add(new Book(3, "Joshua Bloch", "Effective Java"));

        return books;
     }
}