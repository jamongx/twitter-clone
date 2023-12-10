package com.jason.twitter.userservice.repository;


import com.jason.twitter.userservice.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findByFollowersId(Long followerId);

    List<Follow> findByFollowingId(Long followerId);

}
