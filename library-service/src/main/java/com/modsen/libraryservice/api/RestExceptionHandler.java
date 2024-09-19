package com.modsen.libraryservice.api;

import com.modsen.libraryservice.exceptions.BadRequestException;
import com.modsen.libraryservice.exceptions.ExceptionMessage;
import com.modsen.libraryservice.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleOtherException(Throwable e) {
        logger.error("Internal server error", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionMessage("if the error persists, please contact developers", "internal server error"));
    }
}
