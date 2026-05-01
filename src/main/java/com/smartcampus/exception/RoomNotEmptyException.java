package com.smartcampus.exception;

/**
 * Thrown when attempting to delete a room that still has sensors assigned.
 * Enforces referential integrity by preventing orphaned sensor references.
 * Results in HTTP 409 Conflict response.
 *
 * @author Aamir
 */
public class RoomNotEmptyException extends RuntimeException {
    
    /**
     * Constructs a RoomNotEmptyException with a detailed message.
     *
     * @param message description of the conflict (typically includes room ID and sensor count)
     */
    public RoomNotEmptyException(String message) {
        super(message);
    }
}