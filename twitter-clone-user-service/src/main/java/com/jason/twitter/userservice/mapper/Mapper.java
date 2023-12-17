package com.jason.twitter.userservice.mapper;

import com.jason.twitter.userservice.dto.*;
import com.jason.twitter.userservice.entity.Follow;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.entity.UserProfile;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Mapper {

    public static UserProfileDto mapToUserProfileDto(UserProfile userProfile) {
        return new UserProfileDto(
                //userProfile.getUser().getId(),
                userProfile.getId(),
                userProfile.getAvatarUrl(),
                userProfile.getDisplayName(),
                userProfile.getBio(),
                userProfile.isActive(),
                userProfile.getBirthDate());
    }


    public static FollowerDto mapFollowerDto(Follow follow, Function<Long, Optional<User>> userFinder) {
        return userFinder.apply(follow.getFollowingId())
                .map(user -> mapToFollowerDto(follow.getId(), user))
                .orElse(null);
    }



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

    public static List<UserDto> mapToUserDtos(List<User> users) {
        return users.stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getUsername(),
                        user.getUserProfile().getDisplayName(),
                        user.getEmail(),
                        user.getUserProfile().getBio(),
                        user.getUserProfile().getAvatarUrl(),
                        user.getUserProfile().isActive(),
                        user.getUserProfile().getBirthDate()))
                .collect(Collectors.toList());
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
