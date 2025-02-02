package com.test.backend.services.users;

import com.test.backend.models.User;
import com.test.backend.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserServices {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    public UserServices(PasswordEncoder encoder, UserRepository userRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }
    
    public void register(User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setIsAdmin(user.getIsAdmin());
        userRepository.save(newUser);
    }
}
