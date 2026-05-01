package com.smartcampus.exception;

/**
 * Thrown when a sensor cannot process operations due to its current state.
 * Common reasons: sensor under maintenance, offline, or in error state.
 * Results in HTTP 503 Service Unavailable response.
 *
 * @author Aamir
 */
public class SensorUnavailableException extends RuntimeException {
    
    /**
     * Constructs a SensorUnavailableException with a detailed message.
     *
     * @param message description of why the sensor is unavailable
     */
    public SensorUnavailableException(String message) {
        super(message);
    }
}