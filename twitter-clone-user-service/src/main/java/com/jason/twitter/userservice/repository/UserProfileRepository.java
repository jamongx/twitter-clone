package com.jason.twitter.userservice.repository;

import com.jason.twitter.userservice.entity.UserProfile;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {


    @Modifying(clearAutomatically = true)
    @Query("UPDATE UserProfile u SET u.active = :status WHERE u.id = :id")
    int updateUserProfileActiveStatus(@Param("id") Long id, @Param("status") Boolean active);


    @Modifying(clearAutomatically = true)
    @Query("UPDATE UserProfile u SET u.avatarUrl = :avatarUrl WHERE u.id = :id")
    int updateUserProfileAvatarURL(@Param("id") Long id, @Param("avatarUrl") String avatarUrl);

}
