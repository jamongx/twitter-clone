package com.jason.twitter.userservice.utils;

import com.jason.twitter.userservice.constants.SecurityConstants;
import com.jason.twitter.userservice.dto.*;
import com.jason.twitter.userservice.entity.Follow;
import com.jason.twitter.userservice.entity.Role;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.entity.UserProfile;
import com.jason.twitter.userservice.mapper.Mapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestData {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final String DEFAULT_AVATAR_URL = "/images/twitter-default-avatar.jpg";

    public static String getTestUsername(int index) {
        return String.format("testUsername%d", index);
    }

    public static String getTestPassword() {
        return passwordEncoder.encode("testPass");
    }

    public static String getTestDisplayName(int index) {
        return String.format("testDisplayName%d", index);
    }

    public static String getTestBio(int index) {
        return String.format("testBio%d", index);
    }

    public static String getTestEmail(int index) {
        return String.format("testEmail%d@example.com", index);
    }

    public static LocalDate getTestBirthDate() {
        return LocalDate.of(1990, 1, 1);
    }

    public static String getTestAvatar() {
        return DEFAULT_AVATAR_URL;
    }


    public static List<Follow> createFollowers (Long userId, List<User> users) {
        return users.stream()
                .filter(user -> !user.getId().equals(userId))
                .map(user -> new Follow(
                        0L,
                        userId,
                        user.getId(),
                        LocalDateTime.now()
                ))
                .collect(Collectors.toList());
    }

    public static List<Follow> createFollowing (Long userId, List<User> users) {
        return users.stream()
                .filter(user -> !user.getId().equals(userId))
                .map(user -> new Follow(
                        null,
                        user.getId(),
                        userId,
                        null
                ))
                .collect(Collectors.toList());
    }

    public static FollowDto createFollowDto (Follow follow) {
        return new FollowDto(
                follow.getFollowersId(),
                follow.getFollowingId()
        );
    }

    public static Follow createFollow (User followers, User following) {
        return new Follow(
                null,
                followers.getId(),
                following.getId(),
                null
        );
    }

    public static List<FollowerDto> createFollowerDtos(Long userId, List<User> users) {
        return users.stream()
            .filter(user -> !user.getId().equals(userId)) // userId와 user.getId()가 같지 않은 경우에만 포함
            .map(user -> new FollowerDto(
                    userId,
                    user.getId(),
                    user.getUserProfile().getAvatarUrl(),
                    user.getUsername(),
                    user.getUserProfile().getDisplayName(),
                    user.getUserProfile().getBio()))
            .collect(Collectors.toList());
    }


    public static RegisterDto createRegisterDto(User user) {
        return new RegisterDto(
                user.getUsername(),
                user.getPassword(),
                user.getUserProfile().getDisplayName(),
                user.getEmail(),
                user.getUserProfile().getBio(),
                user.getUserProfile().getBirthDate().format(FORMATTER));
    }

    public static LoginDto createLoginDto(String testUsername, String testPassword) {
        return new LoginDto(testUsername, testPassword);
    }

    public static List<User> createTestUsers(int count) {
        List<User> users = new ArrayList<>();

        for (int n = 0; n < count; n++) {
            User user = User.builder()
                    .id((long) n)
                    .username(getTestUsername(n))
                    .email(getTestEmail(n))
                    .password(getTestPassword())
                    .updatedAt(LocalDateTime.now())
                    .createdAt(LocalDateTime.now())
                    .build();

            Set<Role> roles = new HashSet<>();
            Role role = createTestRole(SecurityConstants.ROLE_USER);
            roles.add(role);

            UserProfile userProfile = UserProfile.builder()
                    .id((long) n)
                    .avatarUrl(getTestAvatar())
                    .displayName(getTestDisplayName(n))
                    .bio(getTestBio(n))
                    .active(Boolean.TRUE)
                    .birthDate(getTestBirthDate())
                    .updatedAt(LocalDateTime.now())
                    .createdAt(LocalDateTime.now())
                    .roles(roles)
                    .build();

            userProfile.setUser(user);
            user.setUserProfile(userProfile);
            users.add(user);
        }

        return users;
    }

    public static List<UserDto> createTestUserDtos(int count) {
        List<UserDto> userDtos = new ArrayList<>();

        for (int n = 0; n < count; n++) {
            UserDto userDto = new UserDto();
            userDto.setUsername(getTestUsername(n));
            userDto.setDisplayName(getTestDisplayName(n));
            userDto.setEmail(getTestEmail(n));
            userDto.setBio(getTestBio(n));
            userDto.setAvatarUrl(getTestAvatar());
            userDto.setActive(Boolean.TRUE);
            userDto.setBirthDate(getTestBirthDate());
            userDtos.add(userDto);
        }

        return userDtos;
    }

    public static Role createTestRole(String role) {
        switch (role) {
            case SecurityConstants.ROLE_ADMIN:
                // Return a new Role object for admin with the current time as the creation time
                return new Role(1L, SecurityConstants.ROLE_ADMIN, LocalDateTime.now());
            case SecurityConstants.ROLE_USER:
                // Return a new Role object for user with the current time as the creation time
                return new Role(2L, SecurityConstants.ROLE_USER, LocalDateTime.now());
            default:
                //return new Role(0L, "ROLE_DEFAULT", LocalDateTime.now());
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

}
