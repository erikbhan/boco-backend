package no.ntnu.idatt2106.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StatusCodeExceptionHandler {

    @ExceptionHandler(StatusCodeException.class)
    public ResponseEntity<String> handleStatusCodeException(StatusCodeException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }
}