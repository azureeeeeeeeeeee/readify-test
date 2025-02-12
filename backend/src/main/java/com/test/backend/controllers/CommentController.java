package com.test.backend.controllers;

import com.test.backend.DTO.JsonResponse;
import com.test.backend.models.Comment;
import com.test.backend.services.CommentServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentServices commentServices;

    public CommentController(CommentServices commentServices) {
        this.commentServices = commentServices;
    }

    @PostMapping("/{id}")
    public ResponseEntity<JsonResponse<Object>> createComment(@PathVariable Integer id, @RequestBody Comment comment) {
        return commentServices.createComment(comment, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JsonResponse<Object>> deleteComment(@PathVariable Integer id) {
        return commentServices.deleteComment(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JsonResponse<Object>> updateComment(@PathVariable Integer id, @RequestBody Comment comment) {
        return commentServices.updateComment(comment, id);
    }

}
