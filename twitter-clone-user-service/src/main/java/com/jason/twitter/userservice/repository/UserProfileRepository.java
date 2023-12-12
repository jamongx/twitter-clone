package com.jason.twitter.userservice.repository;


import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

}
