package ru.practicum.exception;

import java.time.Instant;
import java.time.LocalDateTime;

public class NotFoundException extends RuntimeException {
    Exception error;
    String message;
    LocalDateTime timeStamp = LocalDateTime.from(Instant.now());

    public NotFoundException(String message) {
            this.message = message;
    }

    public NotFoundException(String message, Exception error) {
        this.message = message;
        this.error = error;
    }
}
