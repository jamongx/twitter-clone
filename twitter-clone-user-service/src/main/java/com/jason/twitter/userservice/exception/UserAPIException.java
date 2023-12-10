package com.jason.twitter.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class UserAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;
}
