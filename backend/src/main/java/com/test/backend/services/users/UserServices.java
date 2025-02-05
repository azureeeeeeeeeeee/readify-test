package com.test.backend.services.users;

import com.test.backend.models.CustomUser;
import com.test.backend.repositories.UserRepository;
import com.test.backend.utilities.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServices {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final AuthenticationManager manager;

    public UserServices(PasswordEncoder encoder, UserRepository userRepository, AuthenticationManager manager) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.manager = manager;
    }
    
    public void register(CustomUser user) {
        CustomUser newUser = new CustomUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setIsAdmin(user.getIsAdmin());
        userRepository.save(newUser);
    }

    public String login(CustomUser user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
        );

        Authentication authentication = manager.authenticate(token);
        System.out.println(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("generating token. . .");
        User userDetails = (User) authentication.getPrincipal();
        Optional<CustomUser> customUser = userRepository.findByUsername(userDetails.getUsername());
        return JwtUtils.generateToken(customUser);
    }
}

