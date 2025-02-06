package com.test.backend.controllers;

import com.test.backend.DTO.JsonResponse;
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
    public ResponseEntity<JsonResponse<Void>> create(@RequestBody Book book) {
        return bookServices.create(book);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<JsonResponse<Object>> update(@PathVariable String isbn, @RequestBody Book book) {
        return bookServices.update(isbn, book);
    }

}
