package com.test.backend.repositories;

import com.test.backend.models.CustomUser;
import com.test.backend.models.Like;
import com.test.backend.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    Boolean existsByUserAndReview(CustomUser user, Review review);

    void deleteByUserAndReview(CustomUser user, Review review);

    Optional<Like> findByUserAndReview(CustomUser user, Review review);

    long countByReview(Review review);
}
