package com.test.backend.DTO;

public class AuthResult {
    public boolean allowed;
    public String message;

    public AuthResult(boolean allowed, String message) {
        this.allowed = allowed;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isAllowed() {
        return allowed;
    }
}
