package com.test.backend.DTO;

import com.test.backend.models.Review;

public class ReviewDTO {
    public Integer id;
    public Integer rating;
    public String comment;

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();
    }
}
