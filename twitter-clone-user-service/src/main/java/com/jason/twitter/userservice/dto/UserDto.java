package com.jason.twitter.userservice.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private Long id;
    private String username;
    private String displayName;
    private String email;
    private String bio;
    private String avatarUrl;
    private Boolean active;
    private LocalDate birthDate;
}

