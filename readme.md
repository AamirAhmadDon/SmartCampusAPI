# Smart Campus API

## Overview

The Smart Campus API is a RESTful web service developed using JAX-RS. It provides a system to manage rooms, sensors, and sensor readings across a university campus. The API follows REST principles, including resource-based design, proper HTTP methods, and meaningful status codes.

The system allows facilities managers and automated systems to monitor and manage environmental data such as temperature, occupancy, and CO2 levels.

---

## Technology Stack

* Java (JDK 17+)
* JAX-RS (Jersey)
* Maven
* Embedded server (Grizzly/Jetty)

---

## How to Run the Project

### 1. Clone the Repository

```
git clone https://github.com/YOUR_USERNAME/SmartCampusAPI.git
cd SmartCampusAPI
```

### 2. Build the Project

```
mvn clean install
```

### 3. Run the Server

```
mvn exec:java
```

### 4. Access the API

Base URL:

```
http://localhost:8080/api/v1
```

---

## API Endpoints

### Discovery Endpoint

* `GET /api/v1`
  Returns API metadata and available resources.

---

### Room Management

* `GET /rooms`
  Returns all rooms

* `POST /rooms`
  Creates a new room

* `GET /rooms/{id}`
  Returns details of a specific room

* `DELETE /rooms/{id}`
  Deletes a room (only if no sensors are assigned)

---

### Sensor Management

* `POST /sensors`
  Creates a new sensor (validates room existence)

* `GET /sensors`
  Returns all sensors

* `GET /sensors?type=CO2`
  Filters sensors by type

---

### Sensor Readings (Nested Resource)

* `GET /sensors/{id}/readings`
  Returns all readings for a sensor

* `POST /sensors/{id}/readings`
  Adds a new reading and updates sensor current value

---

## Sample curl Commands

```
curl -X GET http://localhost:8080/api/v1/rooms

curl -X POST http://localhost:8080/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"LIB-301","name":"Library Room","capacity":50}'

curl -X GET http://localhost:8080/api/v1/sensors

curl -X GET "http://localhost:8080/api/v1/sensors?type=CO2"

curl -X POST http://localhost:8080/api/v1/sensors/TEMP-001/readings \
-H "Content-Type: application/json" \
-d '{"id":"r1","timestamp":1710000000,"value":23.5}'
```

---

## Error Handling

The API implements structured error handling using Exception Mappers:

* **409 Conflict**
  Returned when attempting to delete a room that still contains sensors.

* **422 Unprocessable Entity**
  Returned when creating a sensor with a non-existent room ID.

* **403 Forbidden**
  Returned when adding readings to a sensor under maintenance.

* **500 Internal Server Error**
  Catch-all for unexpected errors.

All errors return JSON responses instead of stack traces.

---

## Logging

The API uses JAX-RS filters for logging:

* Logs incoming HTTP requests (method + URI)
* Logs outgoing responses (status codes)

This ensures centralized and consistent logging across the application.

---

## Design Decisions & Answers

### 1. JAX-RS Resource Lifecycle

By default, JAX-RS creates a new instance of a resource class per request. This avoids concurrency issues but requires careful handling of shared in-memory data structures such as HashMaps to prevent data inconsistency.

---

### 2. HATEOAS (Hypermedia)

HATEOAS allows clients to dynamically navigate the API using links provided in responses instead of relying on static documentation. This improves flexibility and reduces tight coupling between client and server.

---

### 3. Returning IDs vs Full Objects

Returning only IDs reduces bandwidth usage but increases the number of client requests. Returning full objects improves usability but increases payload size. A balanced approach depends on system requirements.

---

### 4. DELETE Idempotency

DELETE is idempotent because repeated deletion requests produce the same outcome. Once a resource is deleted, further DELETE requests will not change the system state.

---

### 5. @Consumes Mismatch

If a client sends data in a format different from `application/json`, JAX-RS will return a **415 Unsupported Media Type** error.

---

### 6. QueryParam vs PathParam

Query parameters are better for filtering because they are optional and flexible, whereas path parameters are better suited for identifying specific resources.

---

### 7. Sub-Resource Locator Pattern

This pattern improves modularity by delegating nested resource handling to separate classes, reducing complexity and improving maintainability.

---

### 8. HTTP 422 vs 404

422 is more appropriate when the request is valid but contains semantic errors (e.g., invalid room ID inside JSON). 404 is used when a resource itself is not found.

---

### 9. Stack Trace Exposure Risk

Exposing stack traces can reveal internal implementation details, making the system vulnerable to attacks. It may expose class names, file structure, and logic flaws.

---

### 10. Logging with Filters

Using filters centralizes logging logic, avoids duplication, and ensures consistency across all endpoints.

---

## Conclusion

This API demonstrates RESTful design principles, proper resource structuring, error handling, and modular architecture using JAX-RS.

---
