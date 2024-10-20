package com.api.library.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LibraryException extends ResponseStatusException {

    private static final Logger logger = LoggerFactory.getLogger(LibraryException.class);

    public LibraryException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
        logError(message);
    }

    private void logError(String message) {
        logger.error("LibraryException: {}", message);
    }
}