package com.jason.twitter.userservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FollowerDto {
    private Long   followId;
    private Long   userId;
    private String avatarUrl;
    private String username;
    private String displayName;
    private String bio;
}

