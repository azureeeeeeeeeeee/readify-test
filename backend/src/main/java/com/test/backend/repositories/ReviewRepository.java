package com.test.backend.repositories;

import com.test.backend.models.Book;
import com.test.backend.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByBook(Book book);
}
