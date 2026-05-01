# Smart Campus API

## Overview

The **Smart Campus API** is a RESTful web service built with Java and Jersey that manages smart campus infrastructure. It provides endpoints for managing campus rooms and IoT sensors deployed across campus facilities, tracking sensor readings, and maintaining relationships between rooms and their sensors.

## Project Information

- **Version**: 1.0-SNAPSHOT
- **Java Version**: 11
- **Framework**: Jersey 2.41 with Grizzly Embedded Server
- **Build Tool**: Maven
- **Author**: Aamir

## Architecture

### Core Components

#### 1. **Resource Classes** (REST Endpoints)
- `RoomResource` - Manages room CRUD operations
- `SensorResource` - Manages sensor CRUD operations
- `SensorReadingResource` - Sub-resource for sensor measurements
- `DiscoveryResource` - API metadata and navigation

#### 2. **Domain Models** (Data Classes)
- `Room` - Represents a physical campus room with sensors
- `Sensor` - Represents an IoT sensor deployed in a room
- `SensorReading` - Represents a single timestamped sensor measurement

#### 3. **Exception Handling**
- `LinkedResourceNotFoundException` - When referenced resource (e.g., room) not found
- `RoomNotEmptyException` - When attempting to delete a room with active sensors
- `SensorUnavailableException` - When sensor is unavailable (e.g., under maintenance)

#### 4. **Exception Mappers** (HTTP Response Mapping)
- `LinkedResourceMapper` - Returns HTTP 422 Unprocessable Entity
- `RoomNotEmptyMapper` - Returns HTTP 409 Conflict
- `SensorUnavailableMapper` - Returns HTTP 503 Service Unavailable
- `GlobalExceptionMapper` - Returns HTTP 500 for unexpected errors

#### 5. **Cross-Cutting Concerns**
- `LoggingFilter` - Logs all HTTP requests and responses
- `DataStore` - Thread-safe in-memory data persistence

### Data Flow

```
HTTP Request
    ↓
LoggingFilter (Request Log)
    ↓
Resource Class (Validation & Business Logic)
    ↓
Exception (if any)
    ↓
Exception Mapper (if exception)
    ↓
LoggingFilter (Response Log)
    ↓
HTTP Response (JSON)
```

## API Endpoints

### Base URL
```
http://localhost:8080/api/v1
```

### Discovery

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | API metadata and resource links |

**Example Response:**
```json
{
  "api": "Smart Campus API",
  "version": "1.0",
  "contact": "admin@smartcampus.ac.uk",
  "description": "RESTful API for managing smart campus infrastructure...",
  "resources": {
    "rooms": "/api/v1/rooms",
    "sensors": "/api/v1/sensors"
  }
}
```

### Rooms Management

| Method | Endpoint | Status | Description |
|--------|----------|--------|-------------|
| GET | `/rooms` | 200 | Get all rooms |
| POST | `/rooms` | 201 | Create a new room |
| GET | `/rooms/{roomId}` | 200 | Get room by ID |
| DELETE | `/rooms/{roomId}` | 204 | Delete room (must be empty) |

#### Create Room

**Request:**
```bash
POST /api/v1/rooms
Content-Type: application/json

{
  "id": "ROOM-101",
  "name": "Lecture Hall A",
  "capacity": 150
}
```

**Response (201 Created):**
```json
{
  "id": "ROOM-101",
  "name": "Lecture Hall A",
  "capacity": 150,
  "sensorIds": []
}
```

#### Error Responses

- **400 Bad Request**: Missing required fields or invalid data
- **409 Conflict**: Room ID already exists

### Sensors Management

| Method | Endpoint | Status | Description |
|--------|----------|--------|-------------|
| GET | `/sensors` | 200 | Get all sensors (optionally filtered) |
| POST | `/sensors` | 201 | Create a new sensor |
| GET | `/sensors/{sensorId}` | 200 | Get sensor by ID |
| PUT | `/sensors/{sensorId}` | 200 | Update sensor |
| DELETE | `/sensors/{sensorId}` | 204 | Delete sensor |
| GET | `/sensors/{sensorId}/readings` | 200 | Get sensor reading history |
| POST | `/sensors/{sensorId}/readings` | 201 | Add new reading |

#### Create Sensor

**Request:**
```bash
POST /api/v1/sensors
Content-Type: application/json

{
  "id": "SENSOR-001",
  "type": "TEMPERATURE",
  "status": "ACTIVE",
  "currentValue": 22.5,
  "roomId": "ROOM-101"
}
```

**Response (201 Created):**
```json
{
  "id": "SENSOR-001",
  "type": "TEMPERATURE",
  "status": "ACTIVE",
  "currentValue": 22.5,
  "roomId": "ROOM-101"
}
```

#### Filter Sensors by Type

**Request:**
```bash
GET /api/v1/sensors?type=TEMPERATURE
```

**Response (200 OK):**
```json
[
  {
    "id": "SENSOR-001",
    "type": "TEMPERATURE",
    "status": "ACTIVE",
    "currentValue": 22.5,
    "roomId": "ROOM-101"
  }
]
```

#### Update Sensor

**Request:**
```bash
PUT /api/v1/sensors/SENSOR-001
Content-Type: application/json

{
  "status": "MAINTENANCE",
  "currentValue": 22.5,
  "type": "TEMPERATURE",
  "roomId": "ROOM-101"
}
```

**Response (200 OK):**
```json
{
  "id": "SENSOR-001",
  "type": "TEMPERATURE",
  "status": "MAINTENANCE",
  "currentValue": 22.5,
  "roomId": "ROOM-101"
}
```

### Sensor Readings (Measurements)

#### Get Reading History

**Request:**
```bash
GET /api/v1/sensors/SENSOR-001/readings
```

**Response (200 OK):**
```json
[
  {
    "id": "reading-uuid-1",
    "timestamp": 1699564800000,
    "value": 22.5
  },
  {
    "id": "reading-uuid-2",
    "timestamp": 1699564860000,
    "value": 22.7
  }
]
```

#### Add New Reading

**Request:**
```bash
POST /api/v1/sensors/SENSOR-001/readings
Content-Type: application/json

{
  "value": 23.2
}
```

**Response (201 Created):**
```json
{
  "id": "reading-uuid-3",
  "timestamp": 1699564920000,
  "value": 23.2
}
```

**Note**: The API auto-generates the reading ID and timestamp if not provided.

## HTTP Status Codes

| Status | Description |
|--------|-------------|
| 200 | OK - Request successful |
| 201 | Created - Resource created successfully |
| 204 | No Content - Deletion successful |
| 400 | Bad Request - Invalid input or missing required fields |
| 404 | Not Found - Resource does not exist |
| 409 | Conflict - Resource already exists or constraint violation |
| 422 | Unprocessable Entity - Semantic error (e.g., referenced room not found) |
| 503 | Service Unavailable - Sensor under maintenance |
| 500 | Internal Server Error - Unexpected server error |

## Building and Running

### Prerequisites
- Java 11 or higher
- Maven 3.6+

### Build Project
```bash
mvn clean compile
```

### Package as JAR
```bash
mvn clean package
```

### Run the Server
```bash
mvn exec:java -Dexec.mainClass="com.smartcampus.Main"
```

Or run the packaged JAR:
```bash
java -jar target/SmartCampusAPI-1.0-SNAPSHOT.jar
```

The server will start and listen on `http://localhost:8080/api/v1`

## Testing with cURL

### List all rooms
```bash
curl http://localhost:8080/api/v1/rooms
```

### Create a room
```bash
curl -X POST http://localhost:8080/api/v1/rooms \
  -H "Content-Type: application/json" \
  -d '{
    "id": "ROOM-101",
    "name": "Lecture Hall A",
    "capacity": 150
  }'
```

### Create a sensor
```bash
curl -X POST http://localhost:8080/api/v1/sensors \
  -H "Content-Type: application/json" \
  -d '{
    "id": "SENSOR-001",
    "type": "TEMPERATURE",
    "status": "ACTIVE",
    "roomId": "ROOM-101"
  }'
```

### Add a sensor reading
```bash
curl -X POST http://localhost:8080/api/v1/sensors/SENSOR-001/readings \
  -H "Content-Type: application/json" \
  -d '{
    "value": 22.5
  }'
```

## Key Design Principles

### 1. **Separation of Concerns**
- Resource classes handle HTTP concerns
- Model classes represent domain entities
- Exception mappers standardize error responses
- Filters handle cross-cutting concerns

### 2. **Validation Strategy**
- Input validation with appropriate error messages
- Foreign key validation (room existence for sensors)
- Business rule enforcement (non-empty room deletion)
- Sensor status validation (maintenance state)

### 3. **Data Integrity**
- Thread-safe concurrent data structures
- Automatic sensor linking/unlinking with rooms
- Referential integrity checks

### 4. **API Design**
- Idempotent GET operations
- Proper HTTP method semantics (POST for creation, PUT for update, DELETE for deletion)
- RESTful resource structure
- Discoverable API with navigation links

### 5. **Error Handling**
- Custom exceptions for specific error scenarios
- Standardized JSON error responses
- Appropriate HTTP status codes
- Detailed error messages for debugging

## Limitations and Future Improvements

### Current Limitations
1. **In-Memory Storage**: Data is lost on server restart. Consider implementing database persistence (e.g., PostgreSQL, MongoDB)
2. **No Authentication**: All endpoints are publicly accessible. Add JWT or OAuth2
3. **No Rate Limiting**: Can be exploited by repeated requests
4. **Limited Querying**: No advanced filtering, pagination, or sorting
5. **Soft Delete Not Implemented**: Deletions are permanent

### Recommended Enhancements
1. Replace `DataStore` with a persistent database using JPA/Hibernate
2. Implement user authentication and authorization
3. Add API versioning (`/api/v2`, etc.)
4. Implement pagination for list endpoints
5. Add request rate limiting
6. Implement proper logging framework (e.g., Log4j2, SLF4j)
7. Add unit tests using JUnit 5
8. Add integration tests using Testcontainers
9. Document API using Swagger/OpenAPI
10. Implement GraphQL as alternative query interface

## Dependencies

```xml
<!-- Jersey REST Framework -->
org.glassfish.jersey.containers:jersey-container-grizzly2-http:2.41
org.glassfish.jersey.inject:jersey-hk2:2.41

<!-- JSON Processing -->
org.glassfish.jersey.media:jersey-media-json-jackson:2.41
```

## Contact

For questions or support regarding this API, contact: `admin@smartcampus.ac.uk`

## License

Proprietary - Smart Campus Initiative

---

**Last Updated**: April 2026
**API Version**: 1.0
**Java Version**: 11
