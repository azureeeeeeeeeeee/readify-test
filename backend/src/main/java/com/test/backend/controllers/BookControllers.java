package com.test.backend.controllers;

import com.test.backend.models.Book;
import com.test.backend.services.BookServices;
import com.test.backend.utilities.UserUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookControllers {
    private final BookServices bookServices;

    public BookControllers(BookServices bookServices) {
        this.bookServices = bookServices;
    }

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody Book book) {
        return bookServices.create(book);
    }

}
