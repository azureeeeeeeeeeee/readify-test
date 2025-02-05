package com.test.backend.repositories;

import com.test.backend.models.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<CustomUser, Integer> {
    Optional<CustomUser> findByUsername(String username);
}
