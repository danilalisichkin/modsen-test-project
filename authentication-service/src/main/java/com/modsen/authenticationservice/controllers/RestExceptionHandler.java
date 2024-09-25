package com.modsen.authenticationservice.controllers;

import com.modsen.authenticationservice.exceptions.BadRequestException;
import com.modsen.authenticationservice.exceptions.DataUniquenessConflictException;
import com.modsen.authenticationservice.exceptions.ExceptionMessage;
import com.modsen.authenticationservice.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @ExceptionHandler({ResourceNotFoundException.class, EntityNotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Throwable e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionMessage(e.getMessage(), "resource not found"));
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(Throwable e) {
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

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionMessage(cause,"validation error"));
    }

    @ExceptionHandler({DataIntegrityViolationException.class, DataUniquenessConflictException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(Throwable e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ExceptionMessage("data with the same unique field already exists, please use other value", "data uniqueness conflict"));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(Throwable e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ExceptionMessage("provided jwt in authorization header of request is expired, please login again", "jwt expired"));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(Throwable e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionMessage("some of provided user credentials are invalid or user doesn't exist", "invalid user credentials"));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleOtherException(Throwable e) {
        logger.error("Internal server error", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionMessage("if the error persists, please contact developers", "internal server error"));
    }
}
