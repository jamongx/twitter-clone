package com.jason.twitter.userservice.exception;

import com.jason.twitter.userservice.dto.ErrorDetailsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserAPIException.class)
    public ResponseEntity<ErrorDetailsDto> handleUserAPIException(UserAPIException exception, WebRequest request) {
        ErrorDetailsDto errorDetails = new ErrorDetailsDto(LocalDateTime.now(), exception.getMessage(), request.getDescription(false));
        logger.error("UserAPIException: {}", exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDto> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetailsDto errorDetails = new ErrorDetailsDto(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        logger.error("ResourceNotFoundException: {}", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorDetailsDto> handleDuplicateResourceException(DuplicateResourceException ex, WebRequest request) {
        ErrorDetailsDto errorDetails = new ErrorDetailsDto(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        logger.error("DuplicateResourceException: {}", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsDto> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDetailsDto errorDetails = new ErrorDetailsDto(LocalDateTime.now(), "An error occurred", request.getDescription(false));
        logger.error("Exception: {}", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
