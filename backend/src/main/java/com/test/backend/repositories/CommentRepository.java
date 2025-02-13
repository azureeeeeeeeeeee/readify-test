package com.test.backend.repositories;

import com.test.backend.models.Comment;
import com.test.backend.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByReview(Review review);
}
