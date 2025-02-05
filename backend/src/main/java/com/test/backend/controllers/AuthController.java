package com.test.backend.controllers;

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
    public ResponseEntity<String> register(@RequestBody CustomUser user) {
        userServices.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("user registered");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CustomUser user) {
        String token = userServices.login(user);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
