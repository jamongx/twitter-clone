package com.jason.twitter.userservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FollowDto {
    private Long followersId;
    private Long followingId;
}
