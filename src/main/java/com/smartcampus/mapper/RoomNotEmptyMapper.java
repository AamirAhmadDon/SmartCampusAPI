package com.smartcampus.mapper;

import com.smartcampus.exception.RoomNotEmptyException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;

/**
 * Exception mapper for RoomNotEmptyException.
 * Handles cases where deletion is attempted on a room that still contains sensors.
 * Returns HTTP 409 Conflict to indicate a resource constraint violation.
 *
 * @author Aamir
 */
@Provider
public class RoomNotEmptyMapper implements ExceptionMapper<RoomNotEmptyException> {
    
    /**
     * Converts RoomNotEmptyException to an HTTP response.
     * The HTTP 409 status indicates the request conflicts with the current state of the resource.
     *
     * @param ex the exception containing details about the conflicting room
     * @return a 409 Conflict response with error details
     */
    @Override
    public Response toResponse(RoomNotEmptyException ex) {
        return Response.status(409)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(Map.of("error", "Conflict", "message", ex.getMessage()))
                       .build();
    }
}