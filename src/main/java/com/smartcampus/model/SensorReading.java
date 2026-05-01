package com.smartcampus.model;

/**
 * Represents a single timestamped sensor measurement.
 * Captures the measured value along with the exact time of measurement.
 * Immutable after creation for data integrity.
 *
 * @author Aamir
 */
public class SensorReading {
    private String id;
    private long timestamp;
    private double value;

    /**
     * Default constructor for JSON deserialization.
     */
    public SensorReading() {}

    /**
     * Constructs a SensorReading with complete information.
     *
     * @param id the unique reading identifier
     * @param timestamp the time of measurement (milliseconds since epoch)
     * @param value the measured sensor value
     */
    public SensorReading(String id, long timestamp, double value) {
        this.id = id;
        this.timestamp = timestamp;
        this.value = value;
    }

    /**
     * Gets the unique reading identifier.
     * @return the reading ID
     */
    public String getId() { return id; }

    /**
     * Sets the unique reading identifier.
     * @param id the reading ID to set
     */
    public void setId(String id) { this.id = id; }

    /**
     * Gets the timestamp of the measurement.
     * @return the timestamp in milliseconds since epoch
     */
    public long getTimestamp() { return timestamp; }

    /**
     * Sets the timestamp of the measurement.
     * @param timestamp the timestamp in milliseconds since epoch
     */
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    /**
     * Gets the measured sensor value.
     * @return the sensor reading value
     */
    public double getValue() { return value; }

    /**
     * Sets the measured sensor value.
     * @param value the sensor reading value to set
     */
    public void setValue(double value) { this.value = value; }
}