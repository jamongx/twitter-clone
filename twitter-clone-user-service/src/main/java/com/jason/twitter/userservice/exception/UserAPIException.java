package com.jason.twitter.userservice.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class UserAPIException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final HttpStatus status;

    public UserAPIException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}

