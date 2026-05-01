package com.smartcampus.model;

/**
 * Represents a physical sensor deployed in a smart campus room.
 * Tracks sensor type, operational status, current readings, and room assignment.
 * Supports various sensor types (temperature, humidity, motion, CO2, etc.).
 *
 * @author Aamir
 */
public class Sensor {
    private String id;
    private String type;
    private String status;
    private double currentValue;
    private String roomId;

    /**
     * Default constructor for JSON deserialization.
     */
    public Sensor() {}

    /**
     * Constructs a Sensor with complete information.
     *
     * @param id the unique sensor identifier
     * @param type the sensor type (e.g., TEMPERATURE, HUMIDITY, MOTION)
     * @param status the operational status (e.g., ACTIVE, INACTIVE, MAINTENANCE)
     * @param currentValue the most recent sensor reading value
     * @param roomId the ID of the room where this sensor is deployed
     */
    public Sensor(String id, String type, String status, double currentValue, String roomId) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.currentValue = currentValue;
        this.roomId = roomId;
    }

    /**
     * Gets the unique sensor identifier.
     * @return the sensor ID
     */
    public String getId() { return id; }

    /**
     * Sets the unique sensor identifier.
     * @param id the sensor ID to set
     */
    public void setId(String id) { this.id = id; }

    /**
     * Gets the sensor type.
     * @return the type (e.g., TEMPERATURE, HUMIDITY, MOTION, CO2)
     */
    public String getType() { return type; }

    /**
     * Sets the sensor type.
     * @param type the type to set
     */
    public void setType(String type) { this.type = type; }

    /**
     * Gets the operational status of the sensor.
     * @return the status (e.g., ACTIVE, INACTIVE, MAINTENANCE)
     */
    public String getStatus() { return status; }

    /**
     * Sets the operational status of the sensor.
     * @param status the status to set
     */
    public void setStatus(String status) { this.status = status; }

    /**
     * Gets the most recent sensor reading value.
     * @return the current value
     */
    public double getCurrentValue() { return currentValue; }

    /**
     * Sets the most recent sensor reading value.
     * @param currentValue the value to set
     */
    public void setCurrentValue(double currentValue) { this.currentValue = currentValue; }

    /**
     * Gets the ID of the room where this sensor is deployed.
     * @return the room ID
     */
    public String getRoomId() { return roomId; }

    /**
     * Sets the ID of the room where this sensor is deployed.
     * @param roomId the room ID to set
     */
    public void setRoomId(String roomId) { this.roomId = roomId; }
}