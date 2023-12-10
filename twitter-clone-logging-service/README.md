# Overview

- Originally, we planned to develop the entire logging service, but it seems that in practice, companies often use various solutions like the ELK Stack or Kafka. Therefore, we've paused the design process to explore actual best practices. We're seeking to make an efficient choice for log collection and data analysis.

## Purpose

- This document outlines the design and structure of the Logging Service, a key component of the Twitter Clone project.
- The main goal of the Logging Service is to manage and record logs of incoming requests to the system. This enables the monitoring of request handling processes and system performance, providing essential information for troubleshooting and optimization.

## Scope

- The Logging Service serves as the primary component for logging all external communications, effectively collecting, processing, and storing logs from various microservices within the ecosystem.
- Utilizing advanced logging mechanisms and tools, this service collects and analyzes log data, offering vital insights for system performance monitoring and incident response.

## Service Dependencies

- All Services of Twitter Clone

# Technologies

## Development Environment

- Build Tool: Maven
- Code Repository: GitHub
- IDE: Intellij IDEA

## Frameworks and Libraries (Dependencies)

- Spring Boot v3.1.4
- spring-boot-starter-web
- spring-cloud-starter-config
- spring-cloud-starter-netflix-eureka-client
- spring-boot-starter-data-mongodb

## Database

- Mongodb v7.0.3 (Community Version)

# Config File

- Type: `YAML (yml)`
- Profile: `dev`, `prod`, `test`

## Local Config File

- Location: `src/main/resources/application.yml`

```yml
server:
  port: 9020

spring:
  application:
    name: LOGGING-SERVICE
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

- `https://github.com/jamongx/twitter-clone/twitter-clone-config-server-repo/logging-service-dev.yml`

```yml
spring:
  data:
    mongodb:
      host: localhost
      port: 27017
      database: logging_service_db
      username: admin_logging
      password: 1234
```

# User Story

# Data Model

## Entities

- Detailed description of the entity classes and their relationships, including attributes and types.

## Database Schema

# API design

## API Endpoints

- Detailed specification of the API, including paths, methods, request/response bodies, status codes, and headers.
