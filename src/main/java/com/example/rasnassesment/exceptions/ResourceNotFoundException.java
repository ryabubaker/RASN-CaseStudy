package com.example.rasnassesment.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    // Constructor that takes a message as a parameter
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
