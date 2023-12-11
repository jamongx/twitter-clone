package com.jason.twitter.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for handling duplicate resource scenarios.
 * This exception is thrown when an attempt to create a resource
 * that already exists is made.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateResourceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DuplicateResourceException(String message) {
        super(message);
    }
}
