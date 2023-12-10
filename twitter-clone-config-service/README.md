# Overview
## Purpose
- This document articulates the design and structure of the Config Service, a component of the Twitter Clone project.
- The primary objective of Config Service is to provide centralized configuration management across all microservices, ensuring a consistent and dynamic configuration source.


## Scope
- The Config Service centralizes configurations related to all services in the ecosystem, making it easier to manage and modify system behavior without requiring system restarts or rebuilds.
- It interacts with a git-based repository where all configuration files are stored and provides APIs for communication and fetching configurations for other microservices in the ecosystem.


## Service Dependencies
- All Services in Twitter Clone


# Technologies

## Development Environment
- Build Tool: Maven
- Code Repository: GitHub
- IDE: Intellij IDEA

## Frameworks and Libraries (Dependencies)
- Spring Boot v3.1.4
- spring-cloud-config-server
- spring-cloud-starter-netflix-eureka-client


# Config File
- Type: `YAML (yml)`
- Profile: `dev`, `prod`, `test`

## Local Config File
- `src/main/resources/application.yml`
```yml
server:
  port: 9050

spring:
  application:
    name: CONFIG-SERVICE
  profiles:
    active: dev
  cloud:
    config:
      server:
        git:
          uri: https://github.com/jamongx/twitter-clone-config-server-repo
          clone-on-start: true
          default-label: main

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:9040/eureka/
```


# User Story

# Data Model
## Entities
- Detailed description of the entity classes and their relationships, including attributes and types.

## Database Schema

# API design
## API Endpoints
- Detailed specification of the API, including paths, methods, request/response bodies, status codes, and headers.