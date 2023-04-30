package com.example.elevator.exception;

public class InvalidSystemStateException extends RuntimeException {
    public InvalidSystemStateException(String message) {
        super(message);
    }
}
