package com.smartcampus.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Discovery resource providing API metadata and endpoint information.
 * Serves as the root endpoint for the Smart Campus API.
 * Base path: /api/v1
 *
 * <p>This endpoint returns general API information including:
 * <ul>
 *   <li>API name and version</li>
 *   <li>Contact information</li>
 *   <li>Links to main resource endpoints (rooms, sensors)</li>
 * </ul>
 *
 * @author Aamir
 */
@Path("/")
public class DiscoveryResource {

    /**
     * Retrieves API discovery information and resource links.
     * Provides metadata about the API and navigation links to main endpoints.
     *
     * @return HTTP 200 OK with API information and resource links
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response discover() {
        Map<String, Object> info = new HashMap<>();
        info.put("api", "Smart Campus API");
        info.put("version", "1.0");
        info.put("contact", "admin@smartcampus.ac.uk");
        info.put("description", "RESTful API for managing smart campus infrastructure including rooms and sensors");

        Map<String, String> links = new HashMap<>();
        links.put("rooms", "/api/v1/rooms");
        links.put("sensors", "/api/v1/sensors");
        info.put("resources", links);

        return Response.ok(info).build();
    }
}