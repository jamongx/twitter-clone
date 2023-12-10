package com.jason.twitter.userservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterDto {
    private String username;
    private String password;
    private String displayName;
    private String email;
    private String bio;
    private String birthDate;
}

