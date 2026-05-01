package com.smartcampus.resource;

import com.smartcampus.DataStore;
import com.smartcampus.exception.SensorUnavailableException;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * Sub-resource for managing sensor readings (measurements) within SensorResource.
 * Handles all read operations on sensor measurement history.
 * Accessible via /api/v1/sensors/{sensorId}/readings
 *
 * <p>Supported operations:
 * <ul>
 *   <li>GET /sensors/{sensorId}/readings - Retrieve reading history for a sensor</li>
 *   <li>POST /sensors/{sensorId}/readings - Add a new reading to the sensor</li>
 * </ul>
 *
 * @author Aamir
 * @see SensorResource
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private final String sensorId;

    /**
     * Constructs a SensorReadingResource for a specific sensor.
     * This resource is created as a sub-resource by SensorResource.
     *
     * @param sensorId the ID of the sensor for which to manage readings
     */
    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    /**
     * Retrieves all readings recorded for this sensor.
     * Returns the complete reading history in chronological order.
     *
     * @return HTTP 200 OK with list of readings, or HTTP 404 if sensor not found
     */
    @GET
    public Response getReadings() {
        if (sensorId == null || sensorId.trim().isEmpty()) {
            return Response.status(400).entity(Map.of("error", "Sensor ID must not be empty")).build();
        }
        if (!DataStore.sensors.containsKey(sensorId)) {
            return Response.status(404).entity(Map.of("error", "Sensor not found with ID: " + sensorId)).build();
        }
        List<SensorReading> history = DataStore.readings.getOrDefault(sensorId, new ArrayList<>());
        return Response.ok(history).build();
    }

    /**
     * Adds a new reading (measurement) to this sensor's history.
     * Automatically generates ID and timestamp if not provided.
     * Blocks addition if the sensor is under maintenance (unavailable).
     * Updates the parent sensor's currentValue to the new reading.
     *
     * @param reading the SensorReading object with the measurement value
     * @return HTTP 201 Created with the reading, HTTP 404 if sensor not found,
     *         or HTTP 503 if sensor is unavailable (under maintenance)
     * @throws SensorUnavailableException if the sensor is under maintenance
     */
    @POST
    public Response addReading(SensorReading reading) {
        if (sensorId == null || sensorId.trim().isEmpty()) {
            return Response.status(400).entity(Map.of("error", "Sensor ID must not be empty")).build();
        }
        if (reading == null) {
            return Response.status(400).entity(Map.of("error", "Reading object is required")).build();
        }
        Sensor sensor = DataStore.sensors.get(sensorId);
        if (sensor == null) {
            return Response.status(404).entity(Map.of("error", "Sensor not found with ID: " + sensorId)).build();
        }
        // Block if sensor is under maintenance
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException("Sensor " + sensorId + " is under maintenance and cannot accept readings.");
        }
        // Assign UUID and timestamp if not provided
        if (reading.getId() == null || reading.getId().trim().isEmpty()) {
            reading.setId(UUID.randomUUID().toString());
        }
        if (reading.getTimestamp() == 0) {
            reading.setTimestamp(System.currentTimeMillis());
        }
        // Save reading
        DataStore.readings.computeIfAbsent(sensorId, k -> new ArrayList<>()).add(reading);
        // Update parent sensor's currentValue
        sensor.setCurrentValue(reading.getValue());
        return Response.status(201).entity(reading).build();
    }
}