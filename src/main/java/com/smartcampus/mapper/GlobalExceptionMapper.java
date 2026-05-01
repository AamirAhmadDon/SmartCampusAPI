package com.smartcampus.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Global exception handler for unexpected runtime exceptions.
 * Provides a fallback error response for any exception not specifically mapped.
 * Logs exceptions for debugging and returns a sanitized HTTP 500 response.
 * Prevents exposure of sensitive stack trace information to clients.
 *
 * @author Aamir
 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {
    private static final Logger LOG = Logger.getLogger(GlobalExceptionMapper.class.getName());

    /**
     * Maps any unhandled exception to a generic HTTP 500 Internal Server Error response.
     * The actual error is logged for debugging purposes.
     *
     * @param ex the exception that occurred
     * @return a 500 Internal Server Error response with generic error message
     */
    @Override
    public Response toResponse(Throwable ex) {
        if (ex instanceof WebApplicationException) {
            return ((WebApplicationException) ex).getResponse();
        }
        LOG.severe("Unexpected error: " + ex.getMessage());
        return Response.status(500)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(Map.of("error", "Internal Server Error", "message", "An unexpected error occurred."))
                       .build();
    }
}