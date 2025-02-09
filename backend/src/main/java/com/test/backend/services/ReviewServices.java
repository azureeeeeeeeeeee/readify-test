package com.test.backend.services;

import com.test.backend.DTO.AuthResult;
import com.test.backend.DTO.BookDTO;
import com.test.backend.DTO.JsonResponse;
import com.test.backend.DTO.ReviewDTO;
import com.test.backend.models.Book;
import com.test.backend.models.Review;
import com.test.backend.permissions.ReviewPermissions;
import com.test.backend.repositories.BookRepository;
import com.test.backend.repositories.ReviewRepository;
import com.test.backend.utilities.UserUtils;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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
        response.setData(new ReviewDTO(reviewRepository.save(review)));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // Delete Review
    public ResponseEntity<JsonResponse<Object>> deleteReview(Integer id) {
        Optional<Review> checkReview = reviewRepository.findById(id);
        JsonResponse<Object> response = new JsonResponse<>();
        if (checkReview.isEmpty()) {
            response.setMessage("Book with id " + id + " does not exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Review review = checkReview.get();

        // Authorization
        AuthResult authResult = reviewPermissions.checkDelete(review);
        if (!authResult.isAllowed()) {
            response.setMessage(authResult.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        reviewRepository.delete(review);
        response.setMessage("Review with id " + id + " is successfully deleted");

        return ResponseEntity.ok(response);
    }



    // Update Review
    public ResponseEntity<JsonResponse<Object>> updateReview(Integer id, Review review) {
        Optional<Review> checkReview = reviewRepository.findById(id);
        JsonResponse<Object> response = new JsonResponse<>();
        if (checkReview.isEmpty()) {
            response.setMessage("Review with id " + id + " does not exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Review currReview = checkReview.get();

        // Authorization
        AuthResult authResult = reviewPermissions.checkUpdate(currReview);
        if (!authResult.isAllowed()) {
            response.setMessage(authResult.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        currReview.setComment(review.getComment());
        currReview.setRating(review.getRating());

        response.setMessage("Review with id " + id + " sucessfully updated");
        response.setData(new ReviewDTO(reviewRepository.save(currReview)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    // Find by book
    public ResponseEntity<JsonResponse<Object>> findByBook(String isbn) {
        Optional<Book> checkBook = bookRepository.findById(isbn);
        JsonResponse<Object> response = new JsonResponse<>();
        if (checkBook.isEmpty()) {
            response.setMessage("Book with isbn " + isbn + " does not exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Book book = checkBook.get();

        HashMap<String, Object> data = new HashMap<>();

        List<Review> allReview = reviewRepository.findByBook(book);
        List<ReviewDTO> reviewDTOs = allReview.stream().map(ReviewDTO::new).toList();

        data.put("books", new BookDTO(book));
        data.put("reviews", reviewDTOs);

        response.setMessage("All review for book with isbn " + isbn + " is successfully fetched");
        response.setData(data);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // Find One
    public ResponseEntity<JsonResponse<Object>> findOne(Integer id) {
        Optional<Review> checkReview = reviewRepository.findById(id);
        JsonResponse<Object> response = new JsonResponse<>();
        if (checkReview.isEmpty()) {
            response.setMessage("Review with id " + id + " does not exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.setMessage("Review with id " + id + " successfully retrieved");
        response.setData(new ReviewDTO(checkReview.get()));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    // Find All
    public ResponseEntity<JsonResponse<Object>> findAll() {
        JsonResponse<Object> response = new JsonResponse<>();

        // Authorization
        AuthResult authResult = reviewPermissions.checkFindAll();
        if (!authResult.isAllowed()) {
            response.setMessage(authResult.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        List<Review> allReview = reviewRepository.findAll();
        List<ReviewDTO> review = allReview.stream().map(ReviewDTO::new).toList();

        response.setMessage("All review successfully fetched");
        response.setData(review);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
