package com.smartcampus.mapper;

import com.smartcampus.exception.SensorUnavailableException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;

/**
 * Exception mapper for SensorUnavailableException.
 * Handles cases where a sensor cannot accept operations (e.g., under maintenance).
 * Returns HTTP 503 Service Unavailable status with error details.
 *
 * @author Aamir
 */
@Provider
public class SensorUnavailableMapper implements ExceptionMapper<SensorUnavailableException> {
    
    /**
     * Converts SensorUnavailableException to an HTTP response.
     *
     * @param ex the exception to map
     * @return a 503 Service Unavailable response with error details
     */
    @Override
    public Response toResponse(SensorUnavailableException ex) {
        return Response.status(503)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(Map.of("error", "Service Unavailable", "message", ex.getMessage()))
                       .build();
    }
}
