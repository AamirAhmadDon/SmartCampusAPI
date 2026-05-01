package com.smartcampus.filter;

import javax.ws.rs.container.*;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * HTTP request/response logging filter for all API endpoints.
 * Logs incoming request method, URI, and outgoing response status codes.
 * Provides basic request tracing for debugging and monitoring.
 *
 * <p>Registered as a global filter that applies to all requests via @Provider annotation.
 * Uses Java built-in logging framework.
 *
 * @author Aamir
 */
@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private static final Logger LOG = Logger.getLogger(LoggingFilter.class.getName());

    /**
     * Logs incoming HTTP requests before processing.
     * Records request method and full request URI.
     *
     * @param requestContext the request context with method and URI information
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        LOG.info("REQUEST  >> " + requestContext.getMethod() + " " + requestContext.getUriInfo().getRequestUri());
    }

    /**
     * Logs outgoing HTTP responses after processing.
     * Records the HTTP status code of the response.
     *
     * @param requestContext the request context
     * @param responseContext the response context with status information
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        LOG.info("RESPONSE >> Status: " + responseContext.getStatus());
    }
}