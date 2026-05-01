package com.smartcampus;

import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory data store for Smart Campus API.
 * Maintains three concurrent maps for thread-safe access to domain entities:
 * <ul>
 *   <li>rooms: Maps room IDs to Room objects</li>
 *   <li>sensors: Maps sensor IDs to Sensor objects</li>
 *   <li>readings: Maps sensor IDs to lists of SensorReading objects</li>
 * </ul>
 *
 * <p><strong>Note:</strong> This is an in-memory implementation for educational purposes.
 * In production, replace with a persistent database.
 *
 * @author Aamir
 */
public class DataStore {
    /**
     * Concurrent map storing all rooms indexed by room ID.
     * Thread-safe for concurrent read/write operations.
     */
    public static final Map<String, Room> rooms = new ConcurrentHashMap<>();
    
    /**
     * Concurrent map storing all sensors indexed by sensor ID.
     * Thread-safe for concurrent read/write operations.
     */
    public static final Map<String, Sensor> sensors = new ConcurrentHashMap<>();
    
    /**
     * Concurrent map storing sensor readings history.
     * Maps sensor ID to a list of SensorReading objects in chronological order.
     * Thread-safe for concurrent read/write operations.
     */
    public static final Map<String, List<SensorReading>> readings = new ConcurrentHashMap<>();
}