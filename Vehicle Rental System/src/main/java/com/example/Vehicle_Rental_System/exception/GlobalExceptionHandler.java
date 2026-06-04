package com.example.Vehicle_Rental_System.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 — Entity not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), req.getRequestURI());
    }

    // 409 — Vehicle already booked
    @ExceptionHandler(VehicleUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleUnavailable(
            VehicleUnavailableException ex, HttpServletRequest req) {
        return build(HttpStatus.CONFLICT, "Vehicle Unavailable", ex.getMessage(), req.getRequestURI());
    }

    // 400 — Invalid booking rules
    @ExceptionHandler(InvalidBookingException.class)
    public ResponseEntity<ErrorResponse> handleInvalidBooking(
            InvalidBookingException ex, HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, "Invalid Booking", ex.getMessage(), req.getRequestURI());
    }

    // 400 — @Valid validation failures
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err -> {
            String field = ((FieldError) err).getField();
            errors.put(field, err.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // 500 — Unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex, HttpServletRequest req) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error", ex.getMessage(), req.getRequestURI());
    }

    private ResponseEntity<ErrorResponse> build(
            HttpStatus status, String error, String message, String path) {
        ErrorResponse body = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(error).message(message).path(path).build();
        return new ResponseEntity<>(body, status);
    }


}
