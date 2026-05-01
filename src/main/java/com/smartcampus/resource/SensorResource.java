package com.smartcampus.resource;

import com.smartcampus.DataStore;
import com.smartcampus.exception.LinkedResourceNotFoundException;
import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    @GET
    public Collection<Sensor> getSensors(@QueryParam("type") String type) {
        Collection<Sensor> all = DataStore.sensors.values();
        if (type == null || type.isEmpty()) {
            return all;
        }
        return all.stream()
                  .filter(s -> s.getType().equalsIgnoreCase(type))
                  .collect(Collectors.toList());
    }

    @POST
    public Response createSensor(Sensor sensor) {
        if (sensor.getId() == null || sensor.getId().isEmpty()) {
            return Response.status(400).entity(Map.of("error", "Sensor ID is required")).build();
        }
        // Validate roomId exists
        if (sensor.getRoomId() == null || !DataStore.rooms.containsKey(sensor.getRoomId())) {
            throw new LinkedResourceNotFoundException("Room not found: " + sensor.getRoomId());
        }
        DataStore.sensors.put(sensor.getId(), sensor);
        DataStore.readings.put(sensor.getId(), new ArrayList<>());
        // Link sensor to room
        Room room = DataStore.rooms.get(sensor.getRoomId());
        room.getSensorIds().add(sensor.getId());
        return Response.status(201).entity(sensor).build();
    }

    @GET
    @Path("/{sensorId}")
    public Response getSensor(@PathParam("sensorId") String sensorId) {
        Sensor sensor = DataStore.sensors.get(sensorId);
        if (sensor == null) {
            return Response.status(404).entity(Map.of("error", "Sensor not found")).build();
        }
        return Response.ok(sensor).build();
    }

    // Sub-resource locator — delegates to SensorReadingResource
    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadings(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }

    public static class SensorReadingResource {
        private final String sensorId;

        public SensorReadingResource(String sensorId) {
            this.sensorId = sensorId;
        }

        public String getSensorId() {
            return sensorId;
        }
    }
}