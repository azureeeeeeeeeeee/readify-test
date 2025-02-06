package com.test.backend.services;

import com.test.backend.DTO.BookDTO;
import com.test.backend.DTO.JsonResponse;
import com.test.backend.models.Book;
import com.test.backend.models.CustomUser;
import com.test.backend.permissions.BookPermissions;
import com.test.backend.repositories.BookRepository;
import com.test.backend.utilities.UserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServices {
    private final UserUtils userUtils;
    private final BookRepository bookRepository;
    private final BookPermissions bookPermissions;

    public BookServices(UserUtils userUtils, BookRepository bookRepository, BookPermissions bookPermissions) {
        this.userUtils = userUtils;
        this.bookRepository = bookRepository;
        this.bookPermissions = bookPermissions;
    }


    public ResponseEntity<JsonResponse<Void>> create(Book book) {
        CustomUser user = userUtils.getUser();
        JsonResponse<Void> response = new JsonResponse<>();
        if (!bookPermissions.create(user)) {
            response.setMessage("You are not authorized");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        book.setAddedBy(user);
        bookRepository.save(book);
        response.setMessage("Book created");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    public ResponseEntity<JsonResponse<Object>> update(String isbn, Book book) {
        Optional<Book> checkBook = bookRepository.findById(isbn);
        JsonResponse<Object> response = new JsonResponse<>();
        if (checkBook.isEmpty()) {
            response.setMessage("Book with isbn " + isbn + " is not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Book currBook = checkBook.get();
        currBook.setTitle(book.getTitle());
        currBook.setDescription(book.getDescription());
        currBook.setImageURL(book.getImageURL());
        BookDTO data = new BookDTO(bookRepository.save(currBook));
        response.setData(data);
        response.setMessage("Book with isbn " + isbn + " successfully updated");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
