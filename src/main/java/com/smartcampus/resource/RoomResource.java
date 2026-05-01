package com.smartcampus.resource;

import com.smartcampus.DataStore;
import com.smartcampus.exception.RoomNotEmptyException;
import com.smartcampus.model.Room;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;
import java.util.Map;

/**
 * REST resource for managing rooms in the smart campus.
 * Provides endpoints for CRUD operations on Room entities.
 * Base path: /api/v1/rooms
 *
 * <p>Supported operations:
 * <ul>
 *   <li>GET /rooms - Retrieve all rooms</li>
 *   <li>POST /rooms - Create a new room</li>
 *   <li>GET /rooms/{roomId} - Retrieve a specific room</li>
 *   <li>DELETE /rooms/{roomId} - Delete a room (must have no sensors)</li>
 * </ul>
 *
 * @author Aamir
 */
@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    /**
     * Retrieves all rooms in the system.
     *
     * @return a collection of all Room objects, or empty collection if none exist
     */
    @GET
    public Collection<Room> getAllRooms() {
        return DataStore.rooms.values();
    }

    /**
     * Creates a new room with the provided information.
     * Room ID must be unique and non-empty.
     *
     * @param room the Room object with id, name, and capacity
     * @return HTTP 201 Created with the created room, or HTTP 400/409 on validation errors
     */
    @POST
    public Response createRoom(Room room) {
        if (room == null) {
            return Response.status(400).entity(Map.of("error", "Room object is required")).build();
        }
        if (room.getId() == null || room.getId().trim().isEmpty()) {
            return Response.status(400).entity(Map.of("error", "Room ID is required and must not be empty")).build();
        }
        if (DataStore.rooms.containsKey(room.getId())) {
            return Response.status(409).entity(Map.of("error", "Room already exists with ID: " + room.getId())).build();
        }
        if (room.getCapacity() <= 0) {
            return Response.status(400).entity(Map.of("error", "Room capacity must be a positive number")).build();
        }
        DataStore.rooms.put(room.getId(), room);
        return Response.created(URI.create("/api/v1/rooms/" + room.getId()))
                       .entity(room).build();
    }

    /**
     * Retrieves a specific room by its ID.
     *
     * @param roomId the unique room identifier
     * @return HTTP 200 OK with the Room, or HTTP 404 if room not found
     */
    @GET
    @Path("/{roomId}")
    public Response getRoom(@PathParam("roomId") String roomId) {
        if (roomId == null || roomId.trim().isEmpty()) {
            return Response.status(400).entity(Map.of("error", "Room ID must not be empty")).build();
        }
        Room room = DataStore.rooms.get(roomId);
        if (room == null) {
            return Response.status(404).entity(Map.of("error", "Room not found with ID: " + roomId)).build();
        }
        return Response.ok(room).build();
    }

    /**
     * Deletes a room by its ID.
     * A room can only be deleted if it has no sensors assigned.
     * If deletion attempts on a non-empty room trigger RoomNotEmptyException.
     *
     * @param roomId the unique room identifier
     * @return HTTP 204 No Content on success, HTTP 404 if room not found,
     *         or HTTP 409 Conflict if room still has sensors
     */
    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        if (roomId == null || roomId.trim().isEmpty()) {
            return Response.status(400).entity(Map.of("error", "Room ID must not be empty")).build();
        }
        Room room = DataStore.rooms.get(roomId);
        if (room == null) {
            return Response.status(404).entity(Map.of("error", "Room not found with ID: " + roomId)).build();
        }
        if (!room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Room " + roomId + " still has " + room.getSensorIds().size() + " sensor(s) assigned. Please remove all sensors before deleting.");
        }
        DataStore.rooms.remove(roomId);
        return Response.noContent().build();
    }
}