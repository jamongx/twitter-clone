# Overview
## Purpose
- This document describes the design and structure of the Gateway Service, a critical component of the Twitter Clone project.
- The primary objective of the Gateway Service is to manage and direct the traffic of requests coming into the system, utilizing the Spring Cloud Starter Gateway. This ensures that requests are efficiently routed to their appropriate services.


## Scope
- The Gateway Service acts as the main entry point for all external communications, providing routing capabilities for directing requests to the corresponding microservices within the ecosystem.
- It leverages the Spring Cloud Starter Gateway to achieve efficient load balancing, request filtering, and dynamic route discovery.


## Service Dependencies
- Eureka Service
- Config Service
- Logging Service
- User Service


# Technologies

## Development Environment
- Build Tool: Maven
- Code Repository: GitHub
- IDE: Intellij IDEA

## Frameworks and Libraries (Dependencies)
- Spring Boot v3.1.4
- spring-cloud-starter-gateway
- spring-cloud-starter-config
- spring-cloud-starter-netflix-eureka-client


# Config File
- Type: `YAML (yml)`
- Profile: `dev`, `prod`, `test`

## Local Config File
- `src/main/resources/application.yml`
```yml
server:
  port: 9030

spring:
  application:
    name: GATEWAY-SERVICE
  profiles:
    active: dev
  config:
    import: optional:configserver:http://localhost:8888
  cloud:
    config:
      enabled: true

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:9040/eureka/
```

## Remote Config File
- `https://github.com/jamongx/twitter-clone-config-server-repo/gateway-service-dev.yml`
```yml
spring:
  cloud:
    gateway:
      routes:
        - id: USER-SERVICE
          uri: lb://USER-SERVICE
          predicates:
            - Path=/api/user/**
        - id: NEWS-SERVICE
          uri: lb://NEWS-SERVICE
          predicates:
            - Path=/api/news/**

management:
  endpoints:
    web:
      exposure:
        include: "*"
```

# User Story

# Data Model
## Entities
- Detailed description of the entity classes and their relationships, including attributes and types.

## Database Schema

# API design
## API Endpoints
- Detailed specification of the API, including paths, methods, request/response bodies, status codes, and headers.