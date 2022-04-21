package no.ntnu.idatt2106.exception;

import org.springframework.http.HttpStatus;

public class StatusCodeException extends Exception {
    private final HttpStatus status;
    private final String message;

    public StatusCodeException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
