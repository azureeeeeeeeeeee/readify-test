package com.test.backend.utilities;

import com.test.backend.models.CustomUser;
import com.test.backend.repositories.UserRepository;
import org.springframework.security.core.Authentication;
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
        String subject = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String[] parts = subject.split(":");

        if (parts.length != 2) return null;

        Integer userId = Integer.parseInt(parts[0]);

        Optional<CustomUser> currUser = userRepository.findById(userId);

        return currUser.orElse(null);
    }
}
