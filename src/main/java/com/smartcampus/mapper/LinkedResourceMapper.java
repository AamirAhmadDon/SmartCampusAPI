package com.smartcampus.mapper;

import com.smartcampus.exception.LinkedResourceNotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;

/**
 * Exception mapper for LinkedResourceNotFoundException.
 * Handles cases where a referenced resource (foreign key) does not exist.
 * Returns HTTP 422 Unprocessable Entity with detailed error message.
 *
 * @author Aamir
 */
@Provider
public class LinkedResourceMapper implements ExceptionMapper<LinkedResourceNotFoundException> {
    
    /**
     * Converts LinkedResourceNotFoundException to an HTTP response.
     * The HTTP 422 status indicates the request was well-formed but contains semantic errors.
     *
     * @param ex the exception containing details about the missing resource
     * @return a 422 Unprocessable Entity response with error details
     */
    @Override
    public Response toResponse(LinkedResourceNotFoundException ex) {
        return Response.status(422)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(Map.of("error", "Unprocessable Entity", "message", ex.getMessage()))
                       .build();
    }
}