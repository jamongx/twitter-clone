package com.jason.twitter.userservice.dto;

import com.jason.twitter.userservice.entity.Role;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Long id; // user id
    private String avatarUrl;
    private String username;
    private String email;
    private String displayName;
    private String bio;
    private boolean active;
    LocalDateTime createdAt;
    private LocalDate birthDate;
    private Set<Role> roles;
}
