package com.test.backend.controllers;

import com.test.backend.DTO.JsonResponse;
import com.test.backend.models.Review;
import com.test.backend.services.ReviewServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewServices reviewServices;

    public ReviewController(ReviewServices reviewServices) {
        this.reviewServices = reviewServices;
    }

    @PostMapping("/{isbn}")
    public ResponseEntity<JsonResponse<Object>> createReview(@PathVariable String isbn, @RequestBody Review review) {
        return reviewServices.createReview(isbn, review);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JsonResponse<Object>> deleteReview(@PathVariable Integer id) {
        return reviewServices.deleteReview(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<JsonResponse<Object>> updateReview(@PathVariable Integer id, @RequestBody Review review) {
        return reviewServices.updateReview(id, review);
    }

}
