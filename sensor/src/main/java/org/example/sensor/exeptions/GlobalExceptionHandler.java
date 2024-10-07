package org.example.sensor.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FeignExceptionBadRequest.class)
    public ResponseEntity<ExceptionResponse> handleFeignException(FeignExceptionBadRequest exception) {
        String message = exception.getMessage().split(":")[4].split(",")[0];
        return new ResponseEntity<>(
                new ExceptionResponse(message, LocalDateTime.now()),
                HttpStatus.resolve(exception.status())
        );
    }

    @ExceptionHandler(ValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidException(ValidException exception) {
        return new ResponseEntity<>(
                new ExceptionResponse(exception.getMessage(), LocalDateTime.now()),
                HttpStatus.BAD_REQUEST
        );
    }
}
