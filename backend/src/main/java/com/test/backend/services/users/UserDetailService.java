package com.test.backend.services.users;

import com.test.backend.models.User;
import com.test.backend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> tempUser = userRepository.findByUsername(username);
        if (tempUser.isPresent()) {
            User user = tempUser.get();
            return org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword()).build();
        }
        return null;
    }
}
