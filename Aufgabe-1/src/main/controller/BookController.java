package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @GetMapping
    public List<Book> getAllBooks() {
        return Arrays.asList(
                new Book(1, "Martin Kleppmann", "Designing Data-Intensive Applications"),
                new Book(2, "Max C. Martin", "Code"),
                new Book(3, "Jonas J", "Java")
        );
    }
}
