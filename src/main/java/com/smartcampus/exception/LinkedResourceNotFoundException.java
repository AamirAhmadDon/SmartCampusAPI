package com.smartcampus.exception;

/**
 * Thrown when a referenced resource (e.g., a room for a sensor) cannot be found.
 * Used to validate foreign key relationships between entities.
 * Results in HTTP 422 Unprocessable Entity response.
 *
 * @author Aamir
 */
public class LinkedResourceNotFoundException extends RuntimeException {
    
    /**
     * Constructs a LinkedResourceNotFoundException with a detailed message.
     *
     * @param message a description of the missing resource
     */
    public LinkedResourceNotFoundException(String message) {
        super(message);
    }
}