package com.jason.twitter.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateResourseException extends RuntimeException {
    public DuplicateResourseException(String message) {
        super(message);
    }
}
