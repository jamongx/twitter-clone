package com.jason.twitter.userservice.repository;


import com.jason.twitter.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userId);

    Boolean existsByEmail(String email);

    Optional<User> findByUsernameOrEmail(String userId, String email);

    Boolean existsByUsername(String userId);
}
