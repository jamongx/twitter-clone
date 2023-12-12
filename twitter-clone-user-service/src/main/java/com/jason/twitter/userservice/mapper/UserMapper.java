package com.jason.twitter.userservice.mapper;

import com.jason.twitter.userservice.dto.FollowerDto;
import com.jason.twitter.userservice.dto.JwtAuthResponse;
import com.jason.twitter.userservice.entity.User;

public class UserMapper {
    public static FollowerDto mapToFollowerDto(Long followerId, User user) {
        return new FollowerDto(
                followerId,
                user.getId(),
                user.getUserProfile().getAvatarUrl(),
                user.getUsername(),
                user.getUserProfile().getDisplayName(),
                user.getUserProfile().getBio()
        );
    }

    public static JwtAuthResponse mapToJwtAuthResponse(User user, String token, String tokenType) {
        return new JwtAuthResponse(
                token,
                tokenType,
                user.getId(),
                user.getUserProfile().getAvatarUrl(),
                user.getUsername(),
                user.getEmail(),
                user.getUserProfile().getDisplayName(),
                user.getUserProfile().getBio(),
                user.getUserProfile().isActive(),
                user.getCreatedAt(),
                user.getUserProfile().getBirthDate(),
                user.getUserProfile().getRoles());
    }
}
