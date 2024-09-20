package com.example.rasnassesment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GeneralExceptionHandler {

    // Handle ProviderNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleProviderNotFoundException(ResourceNotFoundException ex) {
        Map<String, Object> body = createResponseBody(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }



    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());

        // Collect field-specific validation errors
        body.put("errors", ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage()
                )));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Handle All Other Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        Map<String, Object> body = createResponseBody(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");

        ex.printStackTrace();

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Response Structure
    private Map<String, Object> createResponseBody(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return body;
    }
}
