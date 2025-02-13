package com.test.backend.services;

import com.test.backend.DTO.JsonResponse;
import com.test.backend.models.CustomUser;
import com.test.backend.models.Like;
import com.test.backend.models.Review;
import com.test.backend.repositories.LikeRepository;
import com.test.backend.repositories.ReviewRepository;
import com.test.backend.utilities.UserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;

@Service
public class LikeServices {
    private final UserUtils userUtils;
    private final LikeRepository likeRepository;
    private final ReviewRepository reviewRepository;

    public LikeServices(UserUtils userUtils, LikeRepository likeRepository, ReviewRepository reviewRepository) {
        this.userUtils = userUtils;
        this.likeRepository = likeRepository;
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public ResponseEntity<JsonResponse<Object>> createLike(Integer id) {
        JsonResponse<Object> response = new JsonResponse<>();
        Optional<Review> checkReview = reviewRepository.findById(id);

        if (checkReview.isEmpty()) {
            response.setMessage("review with id " + id + " is not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Review review = checkReview.get();

//        Optional<Like> checkLike = likeRepository.findByUserAndReview(userUtils.getUser(), review);
        CustomUser user = userUtils.getUser();
        Boolean exists = likeRepository.existsByUserAndReview(user, review);
        if (exists) {
            likeRepository.deleteByUserAndReview(user, review);
            response.setMessage("unliked");
            return ResponseEntity.ok(response);
        }

        Like like = new Like();

        like.setReview(review);
        like.setUser(user);

        likeRepository.save(like);

        long likeCount = likeRepository.countByReview(review);
        response.setData(likeCount);

        response.setMessage("Liked");

        return ResponseEntity.ok(response);
    }
}
