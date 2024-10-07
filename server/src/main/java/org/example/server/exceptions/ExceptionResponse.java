package org.example.server.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExceptionResponse {
    private String message;
    private LocalDateTime time;

    public ExceptionResponse() {
    }

    public ExceptionResponse(String message, LocalDateTime time) {
        this.message = message;
        this.time = time;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
