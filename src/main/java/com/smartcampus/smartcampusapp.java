package com.smartcampus;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * JAX-RS application entry point for Smart Campus API.
 * Configures the base path for all REST endpoints as /api/v1.
 * Jersey automatically scans the classpath for resource classes through ResourceConfig in Main.java.
 *
 * @author Aamir
 */
@ApplicationPath("/api/v1")
public class smartcampusapp extends Application {
    // Jersey auto-scans packages via Main.java ResourceConfig
}