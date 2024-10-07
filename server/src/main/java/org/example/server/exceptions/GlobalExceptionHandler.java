package org.example.server.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DeactivateException.class,
            SensorException.class})
    public ResponseEntity<ExceptionResponse> deactivateException(BaseException e,
                                                                 HttpServletRequest request) {
        return new ResponseEntity<>(new ExceptionResponse(
                e.getMessage(),
                LocalDateTime.now()
        ), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundException(BaseException e,
                                                               HttpServletRequest request) {
        return new ResponseEntity<>(
                new ExceptionResponse(
                        e.getMessage(),
                        LocalDateTime.now()
                ), HttpStatus.NOT_FOUND
        );
    }

//    @ExceptionHandler({MethodArgumentNotValidException.class, HandlerMethodValidationException.class})
//    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(BaseException e,
//                                                                          HttpServletRequest request) {
//        Map<String, String> errors = new HashMap<>();
//        e.getBindingResult().getAllErrors().forEach(error -> {
//            String message = error.getDefaultMessage();
//            if (error instanceof FieldError) {
//                String fieldName = ((FieldError) error).getField();
//                errors.put(fieldName, message);
//            } else {
//                errors.put("global", message);
//            }
//        });
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(
//                e.getMessage(), LocalDateTime.now()
//        ));
//    }

    @ExceptionHandler({
            ValidException.class,
            NameSensorException.class
    })
    public ResponseEntity<ExceptionResponse> nameSensorException(BaseException e,
                                                                 HttpServletRequest request) {
        return new ResponseEntity<>(
                new ExceptionResponse(
                        e.getMessage(),
                        LocalDateTime.now()
                ), HttpStatus.BAD_REQUEST
        );
    }
}
