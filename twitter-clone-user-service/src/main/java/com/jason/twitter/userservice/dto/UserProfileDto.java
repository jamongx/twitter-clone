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
public class UserProfileDto {

    private Long id;
    private String avatarUrl;
    private String displayName;
    private String bio;
    private boolean active;
    private LocalDate birthDate;

}
