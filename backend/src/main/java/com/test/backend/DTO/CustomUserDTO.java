package com.test.backend.DTO;

import com.test.backend.models.CustomUser;

public class CustomUserDTO {
    public String username;

    public CustomUserDTO(CustomUser user) {
        this.username = user.getUsername();
    }
}
