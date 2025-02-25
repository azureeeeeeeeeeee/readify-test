package com.test.backend.controllers;

import com.test.backend.DTO.JsonResponse;
import com.test.backend.models.Book;
import com.test.backend.services.BookServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookControllers {
    private final BookServices bookServices;

    public BookControllers(BookServices bookServices) {
        this.bookServices = bookServices;
    }

    @PostMapping()
    public ResponseEntity<JsonResponse<Void>> createBook(@RequestBody Book book) {
        return bookServices.create(book);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<JsonResponse<Object>> updateBook(@PathVariable String isbn, @RequestBody Book book) {
        return bookServices.update(isbn, book);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<JsonResponse<Object>> deleteBook(@PathVariable String isbn) {
        return bookServices.delete(isbn);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<JsonResponse<Object>> findOne(@PathVariable String isbn) {
        return bookServices.findOne(isbn);
    }

    @GetMapping()
    public ResponseEntity<JsonResponse<Object>> findAll() {
        return bookServices.findAll();
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResponse<Object>> search(@RequestParam String keyword) {
        return bookServices.search(keyword, keyword);
    }

}
