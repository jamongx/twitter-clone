package com.jason.twitter.userservice.mapper;

import com.jason.twitter.userservice.dto.FollowerDto;
import com.jason.twitter.userservice.dto.JwtAuthResponse;
import com.jason.twitter.userservice.dto.UserDto;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.entity.UserProfile;

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

    public static User mapToUser(UserDto userDto) {

        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .build();

        UserProfile userProfile = UserProfile.builder()
                .avatarUrl(userDto.getAvatarUrl())
                .displayName(userDto.getDisplayName())
                .bio(userDto.getBio())
                .active(userDto.getActive())
                .birthDate(userDto.getBirthDate())
                .build();

        userProfile.setUser(user);
        user.setUserProfile(userProfile);
        return user;
    }


    public static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getUserProfile().getDisplayName(),
                user.getEmail(),
                user.getUserProfile().getBio(),
                user.getUserProfile().getAvatarUrl(),
                user.getUserProfile().isActive(),
                user.getUserProfile().getBirthDate());
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
