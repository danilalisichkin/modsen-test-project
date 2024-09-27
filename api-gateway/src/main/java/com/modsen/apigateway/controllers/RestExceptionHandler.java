package com.modsen.apigateway.controllers;

import com.modsen.apigateway.exceptions.ExceptionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class RestExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({NoResourceFoundException.class})
    public ResponseEntity<ExceptionMessage> handleResourceNotFoundException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionMessage(e.getMessage(), "resource not found"));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException e) {
        String[] messages = e.getReason().split(" - ", 2);
        return ResponseEntity
                .status(e.getStatusCode())
                .body(new ExceptionMessage(messages[0], messages[1]));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException e) {
        HttpStatusCode statusCode = e.getStatusCode();
        String message = e.getStatusText();

        String errorMessage;
        switch (statusCode) {
            case HttpStatus.NOT_FOUND:
                errorMessage = "the requested resource was not found";
                break;
            case HttpStatus.FORBIDDEN:
                errorMessage = "access is forbidden";
                break;
            case HttpStatus.UNAUTHORIZED:
                errorMessage = "unauthorized access";
                break;
            default:
                errorMessage = "client error occurred: " + message;
        }

        logger.error("Error while working with feign-client: " + errorMessage, e);

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ExceptionMessage("the service is temporarily unavailable, please try later again", "internal server error"));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleOtherException(Throwable e) {
        logger.error("Internal server error", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionMessage("if the error persists, please contact developers", "internal server error"));
    }
}
