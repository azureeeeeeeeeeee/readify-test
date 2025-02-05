package com.test.backend.services;

import com.test.backend.models.Book;
import com.test.backend.models.CustomUser;
import com.test.backend.permissions.BookPermissions;
import com.test.backend.repositories.BookRepository;
import com.test.backend.utilities.UserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<String> create(Book book) {
        CustomUser user = userUtils.getUser();
        if (!bookPermissions.create(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("you are not authorized");
        }
        book.setAddedBy(user);
        bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.OK).body("book created");
    }
}
