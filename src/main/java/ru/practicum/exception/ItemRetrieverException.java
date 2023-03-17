package ru.practicum.exception;

import java.time.Instant;
import java.time.LocalDateTime;

public class ItemRetrieverException extends RuntimeException {

    Exception error;
    String message;
    LocalDateTime timeStamp = LocalDateTime.from(Instant.now());

    public ItemRetrieverException(String message) {
        this.message = message;
    }

    public ItemRetrieverException(String message, Exception error) {
        this.message = message;
        this.error = error;
    }
}
