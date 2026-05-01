package com.smartcampus;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import java.net.URI;

/**
 * Entry point for the Smart Campus API server.
 * Initializes and starts an embedded Grizzly HTTP server with Jersey REST framework.
 * The server listens on http://localhost:8080/ and serves REST endpoints under /api/v1.
 *
 * <p>Components:
 * <ul>
 *   <li>Resource Classes: Handle HTTP requests and responses</li>
 *   <li>Exception Mappers: Convert exceptions to standardized JSON error responses</li>
 *   <li>Filters: Log all incoming requests and outgoing responses</li>
 * </ul>
 *
 * @author Aamir
 * @see smartcampusapp
 */
public class Main {
    public static final String BASE_URI = "http://localhost:8080/api/v1/";

    /**
     * Main method to start the Smart Campus API server.
     * Automatically discovers and registers resource classes and exception mappers
     * from the com.smartcampus package.
     *
     * @param args command-line arguments (not used)
     * @throws Exception if server initialization fails or I/O error occurs
     */
    public static void main(String[] args) throws Exception {
        ResourceConfig config = new ResourceConfig().packages("com.smartcampus");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
        System.out.println("Smart Campus API running at http://localhost:8080/api/v1/");
        System.out.println("Press ENTER to stop...");
        System.in.read();
        server.stop();
    }
}