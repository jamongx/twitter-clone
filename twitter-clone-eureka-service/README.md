# Overview
## Purpose
- This document articulates the design and structure of the Eureka Service, a component of the Twitter Clone project.
- The primary objective of Eureka Service is to act as a service registry and service discovery mechanism, ensuring that microservices can register themselves and discover other services seamlessly.


## Scope
The Eureka Service utilizes Spring Cloud Netflix Eureka Server to manage service registration and discovery. This includes:

- Allowing services to register themselves with Eureka, indicating that they are available for use.
- Providing a mechanism for services to discover other registered services without hardcoding their addresses.
- Balancing the load among service instances using a client-side load balancing pattern.
- Ensuring system resilience by directing traffic away from failing or faltering service instances.

Through its capabilities, Eureka Service simplifies inter-service communication and enhances the resilience and scalability of the Twitter Clone microservices ecosystem.


## Service Dependencies
- All Services in Twitter Clone


# Technologies

## Development Environment
- Build Tool: Maven
- Code Repository: GitHub
- IDE: Intellij IDEA

## Frameworks and Libraries (Dependencies)
- Spring Boot v3.1.4
- spring-cloud-starter-netflix-eureka-server


# Config File
- Type: `YAML (yml)`
- Profile: `dev`, `prod`, `test`

## Local Config File
- `src/main/resourcesapplication.yml`
```yml
server:
  port: 9040

spring:
  application:
    name: EUREKA-SERVICE
  profiles:
    active: dev
#set Disable eureka client
eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://localhost:9040/eureka
```

# User Story

# Data Model
## Entities
- Detailed description of the entity classes and their relationships, including attributes and types.

## Database Schema

# Custom API design
## API Endpoints
- Detailed specification of the API, including paths, methods, request/response bodies, status codes, and headers.

