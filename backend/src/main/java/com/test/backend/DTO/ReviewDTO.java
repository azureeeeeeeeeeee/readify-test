package com.test.backend.DTO;

import com.test.backend.models.Review;

public class ReviewDTO {
    public Integer id;
    public BookDTO book;
    public Integer rating;
    public String comment;

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.book = new BookDTO(review.getBook());
        this.rating = review.getRating();
        this.comment = review.getComment();
    }
}
