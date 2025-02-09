package com.test.backend.utilities;

import com.test.backend.models.CustomUser;
import com.test.backend.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserUtils {
    private final UserRepository userRepository;

    public UserUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CustomUser getUser() {
        try {
            String subject = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            String[] parts = subject.split(":");

            if (parts.length != 2) return null;

            Integer userId = Integer.parseInt(parts[0]);
            Optional<CustomUser> currUser = userRepository.findById(userId);
            return currUser.orElse(null);
        } catch (NullPointerException | NumberFormatException e) {
            return null;
        }
    }

    public Integer getUserId() {
        try {
            String subject = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            String[] parts = subject.split(":");

            if (parts.length != 2) return null;

            return Integer.parseInt(parts[0]);
        } catch (NullPointerException | NumberFormatException e) {
            return null;
        }
    }

    public Boolean getAdminStatus() {
        try {
            String subject = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            String[] parts = subject.split(":");

            if (parts.length != 2) return false;

            return Boolean.parseBoolean(parts[1]);
        } catch (NullPointerException e) {
            return false;
        }
    }
}