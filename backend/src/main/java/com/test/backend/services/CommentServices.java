package com.test.backend.services;

import com.test.backend.DTO.AuthResult;
import com.test.backend.DTO.JsonResponse;
import com.test.backend.models.Comment;
import com.test.backend.models.Review;
import com.test.backend.permissions.CommentPermissions;
import com.test.backend.repositories.CommentRepository;
import com.test.backend.repositories.ReviewRepository;
import com.test.backend.utilities.UserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServices {
    private final UserUtils userUtils;
    private final CommentRepository commentRepository;
    private final CommentPermissions commentPermissions;
    private final ReviewRepository reviewRepository;

    public CommentServices(UserUtils userUtils, CommentRepository commentRepository, CommentPermissions commentPermissions, ReviewRepository reviewRepository) {
        this.userUtils = userUtils;
        this.commentRepository = commentRepository;
        this.commentPermissions = commentPermissions;
        this.reviewRepository = reviewRepository;
    }

    // Create comment
    public ResponseEntity<JsonResponse<Object>> createComment(Comment comment, Integer id) {
        Optional<Review> checkReview = reviewRepository.findById(id);
        JsonResponse<Object> response = new JsonResponse<>();
        if (checkReview.isEmpty()) {
            response.setMessage("Review with");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Review review = checkReview.get();

        // Authz ? as long as user it authenticated

        comment.setUser(userUtils.getUser());
        comment.setReview(review);

        commentRepository.save(comment);
        response.setMessage("comment created");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Delete Commnet
    public ResponseEntity<JsonResponse<Object>> deleteComment(Integer id) {
        Optional<Comment> checkComment = commentRepository.findById(id);
        JsonResponse<Object> response = new JsonResponse<>();
        if (checkComment.isEmpty()) {
            response.setMessage("Comment with id " + id + " is not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Comment comment = checkComment.get();

        // authz
        AuthResult authResult = commentPermissions.checkDelete(comment);
        if (!authResult.isAllowed()) {
            response.setMessage(authResult.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        commentRepository.delete(comment);

        response.setMessage("Comment with id " + id + " is successfully deleted");
        return ResponseEntity.ok(response);

    }


    // Update comment
    public ResponseEntity<JsonResponse<Object>> updateComment(Comment comment, Integer id) {
        Optional<Comment> checkComment = commentRepository.findById(id);
        JsonResponse<Object> response = new JsonResponse<>();
        if (checkComment.isEmpty()) {
            response.setMessage("Comment not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Comment comment1 = checkComment.get();

        AuthResult authResult = commentPermissions.checkUpdate(comment1);
        if (!authResult.isAllowed()) {
            response.setMessage(authResult.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        comment1.setComment(comment.getComment());
        response.setData(commentRepository.save(comment1));
        response.setMessage("Update succesful");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }



    // Find by id



    // Find by review



    // Find all

}
