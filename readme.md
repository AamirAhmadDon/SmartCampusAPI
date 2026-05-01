# Smart Campus API (JAX-RS)

## Overview

This project implements a RESTful API for managing a Smart Campus system. The API allows management of Rooms, Sensors, and Sensor Readings using JAX-RS principles. It demonstrates resource-based design, validation, nested resources, and structured error handling.

---

## ⚙️ How to Run the Project

### Prerequisites

* Java JDK 17+
* Apache Maven 3.8+

### Steps

```bash
cd C:\Users\Aamir\Desktop\CSA\SmartCampusAPI\SmartCampusAPI
mvn clean install
mvn exec:java -Dexec.mainClass="com.smartcampus.Main"
```

### Base URL

```
http://localhost:8080/api/v1
```

---

## 🔗 API Endpoints

### Rooms

* GET /rooms
* POST /rooms
* GET /rooms/{id}
* DELETE /rooms/{id}

### Sensors

* POST /sensors
* GET /sensors
* GET /sensors?type=CO2

### Sensor Readings

* GET /sensors/{id}/readings
* POST /sensors/{id}/readings

---

## Part 1: Service Architecture & Setup

### JAX-RS Resource Lifecycle

By default, JAX-RS resource classes are instantiated per request (request-scoped). This means a new instance is created for each incoming HTTP request.

This design avoids shared mutable state between requests, reducing the risk of race conditions. However, since this project uses in-memory data structures (e.g., HashMaps and ArrayLists), care must still be taken to ensure thread safety when multiple requests modify shared data. Synchronization or concurrent collections should be used to prevent data inconsistency.

---

### HATEOAS (Hypermedia as the Engine of Application State)

HATEOAS is a REST principle where responses include links to related resources.

This approach benefits client developers by:

* Reducing dependency on external documentation
* Allowing dynamic navigation of the API
* Making APIs more self-descriptive and flexible

Compared to static documentation, HATEOAS enables clients to discover available actions at runtime.

---

## Part 2: Room Management

### IDs vs Full Objects

Returning only IDs reduces bandwidth usage but forces clients to make additional requests to retrieve full details.

Returning full objects increases response size but improves client efficiency by reducing the number of API calls required. A balanced approach depends on use case and performance requirements.

---

### DELETE Idempotency

DELETE is idempotent because:

* The first request deletes the resource
* Subsequent identical requests result in no further changes

If a room is deleted once, repeated DELETE requests either return success or a "not found" response, but do not alter system state further.

---

## Part 3: Sensor Operations

### @Consumes and Media Type Handling

The @Consumes(MediaType.APPLICATION_JSON) annotation ensures that the API only accepts JSON input.

If a client sends data in a different format (e.g., text/plain or application/xml), JAX-RS will reject the request with a 415 Unsupported Media Type error, as it cannot deserialize the input into the expected Java object.

---

### QueryParam vs PathParam for Filtering

Using @QueryParam (e.g., ?type=CO2) is preferred for filtering because:

* It represents optional criteria
* It keeps resource paths clean and consistent
* It supports multiple filters easily

Using path parameters for filtering (e.g., /sensors/type/CO2) makes the API less flexible and harder to extend.

---

## Part 4: Sub-Resource Locator Pattern

### Benefits of Sub-Resources

The Sub-Resource Locator pattern allows delegation of nested resource handling to separate classes.

Advantages:

* Improves code organization
* Reduces complexity in main resource classes
* Enhances maintainability and scalability

Instead of placing all logic in one class, responsibilities are split logically across resources.

---

### Data Consistency

When a new sensor reading is added:

* It is stored in the sensor's reading history
* The parent sensor’s currentValue is updated

This ensures consistency between historical and real-time data.

---

## Part 5: Error Handling & Logging

### HTTP 422 vs 404

HTTP 422 is more appropriate when:

* The request is syntactically correct
* But contains invalid data (e.g., non-existent roomId)

A 404 implies the endpoint itself is missing, while 422 indicates a semantic issue within a valid request.

---

### Security Risks of Stack Traces

Exposing stack traces can reveal:

* Internal class names
* File structures
* Framework details
* Potential vulnerabilities

Attackers can use this information to exploit the system. Therefore, generic error responses are used instead.

---

### Logging with Filters

Using JAX-RS filters for logging is advantageous because:

* It centralizes logging logic
* Avoids repetition in every resource method
* Ensures consistency across all endpoints

Filters act as cross-cutting components, improving maintainability and separation of concerns.

---

## 🧪 Sample curl Commands

```bash
curl -X GET http://localhost:8080/api/v1/rooms

curl -X POST http://localhost:8080/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"R1","name":"Library","capacity":50}'

curl -X GET http://localhost:8080/api/v1/sensors?type=CO2

curl -X POST http://localhost:8080/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":"S1","type":"CO2","status":"ACTIVE","currentValue":0,"roomId":"R1"}'

curl -X DELETE http://localhost:8080/api/v1/rooms/R1
```

---

## Conclusion

This project demonstrates a fully functional RESTful API using JAX-RS, including resource management, validation, nested resources, filtering, and robust error handling aligned with REST principles.
