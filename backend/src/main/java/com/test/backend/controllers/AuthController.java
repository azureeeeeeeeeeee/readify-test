package com.test.backend.controllers;

import com.test.backend.DTO.JsonResponse;
import com.test.backend.models.CustomUser;
import com.test.backend.services.users.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserServices userServices;

    public AuthController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping("/register")
    public ResponseEntity<JsonResponse<Void>> register(@RequestBody CustomUser user) {
        JsonResponse<Void> response = new JsonResponse<>();
        userServices.register(user);
        response.setMessage("User Registered");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JsonResponse<String>> login(@RequestBody CustomUser user) {
        JsonResponse<String> response = new JsonResponse<>();
        String token = userServices.login(user);
        response.setMessage("Login Successful");
        response.setData(token);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
