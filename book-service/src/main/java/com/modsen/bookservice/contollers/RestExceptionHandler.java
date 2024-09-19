package com.modsen.bookservice.contollers;

import com.modsen.bookservice.exceptions.BadRequestException;
import com.modsen.bookservice.exceptions.ExceptionMessage;
import com.modsen.bookservice.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({ResourceNotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionMessage(e.getMessage(), "resource not found"));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionMessage(e.getMessage(), "bad request"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleNoValidException(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errorMap.put(fieldName, errorMessage);
        });

        String cause = "invalid input provided: " + errorMap;

        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .message("validation error")
                .cause(cause)
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionMessage);
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
