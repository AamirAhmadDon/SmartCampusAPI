package com.smartcampus.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a physical room in the smart campus.
 * Maintains information about room location, capacity, and associated sensors.
 * Supports many-to-many relationship with sensors through sensorIds list.
 *
 * @author Aamir
 */
public class Room {
    private String id;
    private String name;
    private int capacity;
    private List<String> sensorIds = new ArrayList<>();

    /**
     * Default constructor for JSON deserialization.
     */
    public Room() {}

    /**
     * Constructs a Room with basic information.
     *
     * @param id the unique room identifier (required)
     * @param name the descriptive name of the room
     * @param capacity the maximum occupancy capacity
     */
    public Room(String id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    /**
     * Gets the unique room identifier.
     * @return the room ID
     */
    public String getId() { return id; }

    /**
     * Sets the unique room identifier.
     * @param id the room ID to set
     */
    public void setId(String id) { this.id = id; }

    /**
     * Gets the descriptive name of the room.
     * @return the room name
     */
    public String getName() { return name; }

    /**
     * Sets the descriptive name of the room.
     * @param name the room name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * Gets the maximum occupancy capacity of the room.
     * @return the room capacity
     */
    public int getCapacity() { return capacity; }

    /**
     * Sets the maximum occupancy capacity of the room.
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) { this.capacity = capacity; }

    /**
     * Gets the list of sensor IDs associated with this room.
     * @return list of sensor identifiers
     */
    public List<String> getSensorIds() { return sensorIds; }

    /**
     * Sets the list of sensor IDs associated with this room.
     * @param sensorIds the sensor ID list to set
     */
    public void setSensorIds(List<String> sensorIds) { this.sensorIds = sensorIds; }
}