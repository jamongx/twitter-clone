package com.jason.twitter.userservice.mapper;

import com.jason.twitter.userservice.dto.FollowerDto;
import com.jason.twitter.userservice.dto.JwtAuthResponse;
import com.jason.twitter.userservice.entity.User;

public class UserMapper {
    public static FollowerDto mapToFollowerDto(Long followerId, User user) {
        return new FollowerDto(
                followerId,
                user.getId(),
                user.getAvatarUrl(),
                user.getUsername(),
                user.getDisplayName(),
                user.getBio()
        );
    }

    public static JwtAuthResponse mapToJwtAuthResponse(User user, String token, String tokenType) {
        return new JwtAuthResponse(
                token,
                tokenType,
                user.getId(),
                user.getAvatarUrl(),
                user.getUsername(),
                user.getEmail(),
                user.getDisplayName(),
                user.getBio(),
                user.isActive(),
                user.getCreatedAt(),
                user.getBirthDate(),
                user.getRoles());
    }
}
