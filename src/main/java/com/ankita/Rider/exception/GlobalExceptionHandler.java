package com.ankita.Rider.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {

        String msg = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity.badRequest().body(
                Map.of(
                        "error", "VALIDATION_ERROR",
                        "message", msg,
                        "timestamp", Instant.now().toString()
                )
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "error", "NOT_FOUND",
                        "message", ex.getMessage(),
                        "timestamp", Instant.now().toString()
                )
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequest(BadRequestException ex) {

        return ResponseEntity.badRequest().body(
                Map.of(
                        "error", "BAD_REQUEST",
                        "message", ex.getMessage(),
                        "timestamp", Instant.now().toString()
                )
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> handleForbidden(ForbiddenException ex) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                Map.of(
                        "error", "FORBIDDEN",
                        "message", ex.getMessage(),
                        "timestamp", Instant.now().toString()
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of(
                        "error", "INTERNAL_SERVER_ERROR",
                        "message", ex.getMessage(),
                        "timestamp", Instant.now().toString()
                )
        );
    }
}