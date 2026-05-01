## Comprehensive Answers to Coursework Questions

### JAX-RS Resource Lifecycle
The JAX-RS resource lifecycle involves several stages from the initial request to the final response. It starts with the client sending a request to a JAX-RS endpoint. The JAX-RS runtime then creates a Java object representing the resource, processes the request by invoking appropriate methods (GET, POST, etc.), and finally returns a response back to the client. Key aspects include resource instantiation, method invocation, and response generation, all managed by JAX-RS.

### HATEOAS (Hypermedia as the Engine of Application State)
HATEOAS is a constraint of the REST application architecture that enables clients to dynamically discover actions they can take based on the current application state. Instead of hard-coding endpoints, clients receive hypermedia links in the representation of resources to navigate the API. This enhances the discoverability and usability of the API.

### Object vs ID Returns
In RESTful design, returning an object provides full context and data about the resource, whereas returning just an ID is more lightweight. Returning IDs is suitable for scenarios where the client needs to reference resources without requiring all the details. However, in many cases, returning complete objects can improve the client experience by reducing the need for additional requests.

### DELETE Idempotency
DELETE operations are idempotent, meaning that performing the same DELETE request multiple times should have the same effect as executing it once. The resource should be deleted after the first request, and subsequent requests should confirm that the resource no longer exists without causing errors or unintended changes to the server state.

### @Consumes Mismatch
The @Consumes annotation in JAX-RS defines the media types that a resource method can consume. A mismatch occurs when the client sends a request with a different media type than what the server expects, which may lead to resolution failure of the resource method. Handle mismatches appropriately by returning a 415 Unsupported Media Type response.

### QueryParam vs PathParam
Query parameters and path parameters serve different purposes in REST APIs. Path parameters are part of the URL path and are used to define resources, while query parameters provide additional filtering, sorting, or pagination options. Understanding when to use each type is crucial for designing clean and consistent API endpoints.

### Sub-Resource Locator Pattern
The Sub-Resource Locator pattern allows RESTful services to create complex resource hierarchies. Sub-resources are accessed through dedicated locators, enabling clear and organized routes that reflect the underlying resource relationships. This pattern helps in achieving a clean and logical API structure.

### HTTP 422 vs 404
HTTP status code 404 indicates that the requested resource was not found, while a 422 status code indicates that the request was well-formed but contained invalid data. It's crucial to use the correct status code to convey accurate information about the error context, helping clients to handle responses appropriately.

### Stack Trace Exposure Risks
Exposing stack traces in production can leak sensitive information about the application’s internal workings. It can aid malicious users in crafting more effective attacks. Always ensure to sanitize error messages and consider logging stack traces only in secure environments, such as development or staging.

### Logging with Filters
Using filters for logging in JAX-RS allows developers to intercept HTTP requests and responses systematically. Logging filters can record important information, such as request and response payloads, headers, and status codes while maintaining separation of concerns. Implementing this can enhance observability while keeping the logging process standardized across the application.