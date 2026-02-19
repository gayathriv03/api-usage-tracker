package com.example.apiusagetracker.exception;

public class InvalidUserHeaderException extends RuntimeException {

    public InvalidUserHeaderException(String message) {
        super(message);
    }
}
