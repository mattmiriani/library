package com.api.library.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LibraryException.class)
    public ResponseEntity<Object> handleLibraryException(LibraryException ex, WebRequest request) {
        return new ResponseEntity<>(this.body(ex, request), ex.getStatusCode());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        return new ResponseEntity<>(this.body(ex, request), ex.getStatusCode());
    }

    private Map<String, Object> body(ResponseStatusException ex, WebRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().format(formatter));
        body.put("status", ex.getStatusCode().value());
        body.put("message", ex.getReason());
        body.put("path", request.getDescription(false).substring(4));
        return body;
    }
}
