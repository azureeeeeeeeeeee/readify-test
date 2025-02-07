package com.test.backend.services;

import com.test.backend.DTO.AuthResult;
import com.test.backend.DTO.JsonResponse;
import com.test.backend.models.Book;
import com.test.backend.models.Review;
import com.test.backend.permissions.ReviewPermissions;
import com.test.backend.repositories.BookRepository;
import com.test.backend.repositories.ReviewRepository;
import com.test.backend.utilities.UserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewServices {
    private final UserUtils userUtils;
    private final ReviewPermissions reviewPermissions;
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    public ReviewServices(UserUtils userUtils, ReviewPermissions reviewPermissions, ReviewRepository reviewRepository, BookRepository bookRepository) {
        this.userUtils = userUtils;
        this.reviewPermissions = reviewPermissions;
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }

    // Create review
    @PreAuthorize("isbn == review.book.isbn")
    public ResponseEntity<JsonResponse<Object>> createReview(String isbn, Review review) {
        Optional<Book> checkBook = bookRepository.findById(isbn);
        JsonResponse<Object> response = new JsonResponse<>();
        if (checkBook.isEmpty()) {
            response.setMessage("Book with isbn " + review.getBook().getIsbn() + " does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Book book = checkBook.get();

        // authorization
        AuthResult authResult = reviewPermissions.checkCreate(book);
        if (!authResult.isAllowed()) {
            response.setMessage(authResult.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        review.setBook(book);
        review.setUser(userUtils.getUser());

        response.setMessage("Your review successfully saved");
        response.setData(reviewRepository.save(review));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
