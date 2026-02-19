package com.example.apiusagetracker.controller.advice;

import com.example.apiusagetracker.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidation(MethodArgumentNotValidException ex,  HttpServletRequest request) {

        log.warn("Validation error at {} : {}",
                request.getRequestURI(),
                ex.getMessage());

        Map<String, String> errors = new java.util.HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 400,
                "errors", errors
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
        log.warn("UserNotFoundException at {} : {}",
                request.getRequestURI(),
                ex.getMessage());
        return Map.of(
                "time", LocalDateTime.now(),
                "error", ex.getMessage()
        );
    }

    @ExceptionHandler(DuplicateUserException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleDuplicateUser(DuplicateUserException ex, HttpServletRequest request) {
        log.warn("DuplicateUserException at {} : {}",
                request.getRequestURI(),
                ex.getMessage());
        return Map.of(
                "time", LocalDateTime.now(),
                "error", ex.getMessage()
        );
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
        log.warn("BadRequestException at {} : {}",
                request.getRequestURI(),
                ex.getMessage());
        return Map.of(
                "time", LocalDateTime.now(),
                "error", ex.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleGeneric(Exception ex,  HttpServletRequest request) {
        log.error("Unexpected error at {}",
                request.getRequestURI(), ex);
        return Map.of(
                "time", LocalDateTime.now(),
                "error", "Something went wrong"
        );


    }
    @ExceptionHandler(InvalidUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleInvalidUser(InvalidUserException ex, HttpServletRequest request) {

        log.warn("InvalidUserHeaderException at {} : {}",
                request.getRequestURI(),
                ex.getMessage());

        return Map.of(
                "timestamp", LocalDateTime.now(),
                "error", ex.getMessage()
        );
    }

    @ExceptionHandler(DatabaseOperationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleDatabase(DatabaseOperationException ex, HttpServletRequest request) {
        log.error("Database error at {} : {}",
                request.getRequestURI(),
                ex.getMessage(),
                ex);
        return Map.of(
                "timestamp", LocalDateTime.now(),
                "error", "Database error occurred"
        );
    }


}
