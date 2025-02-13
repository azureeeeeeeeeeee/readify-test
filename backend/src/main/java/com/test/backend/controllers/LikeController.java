package com.test.backend.controllers;

import com.test.backend.DTO.JsonResponse;
import com.test.backend.services.LikeServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {
    private final LikeServices likeServices;

    public LikeController(LikeServices likeServices) {
        this.likeServices = likeServices;
    }

    @PostMapping("/review/{id}/like")
    public ResponseEntity<JsonResponse<Object>> craeteLike(@PathVariable Integer id) {
        return likeServices.createLike(id);
    }
}
