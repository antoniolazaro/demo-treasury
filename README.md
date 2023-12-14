# Getting Started

This project has the goal to provide a solution for the project WEX Gateway

The archecture is based on microservices and the following technologies:
* Java 17
* Spring Boot 3.2.0
* H2 Database
* Flyway for DB migrations
* Junit, Mockito and AssertJ for unit tests
* OpenFeign for REST calls
* Docker and Docker Compose for containerization
* Postman for API tests
* Swagger for API documentation
* Spring Actuator for health check
* Spring Data JPA for data access
* Spring Validation for validation
* Spring Web for REST API
* Spring Configuration Processor for configuration
* Spring Boot DevTools for development
* Spring Boot Maven Plugin for build

### Links

* Health check: http://localhost:8080/actuator/health
* Swagger: http://localhost:8080/swagger-ui/index.html

### Run as docker image

# docker build -t myorg/myapp .
# docker run -p 8080:8080 myorg/myapp   