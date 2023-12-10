# Overview
## Purpose
- This document articulates the design and structure of the User Service, a component of the Twitter Clone project.
- The primary objective of User Service is to oversee functionalities related to user management, including but not limited to, user profiles, registration, and login.


## Scope
- The User Service encapsulates features such as user registration, login authentication, password resets, and profile management.
- It interacts with a separate authentication service to perform token-based authentication and provides APIs for communication with other microservices in the ecosystem.


## Service Dependencies
- Gateway Service
- Eureka Service
- Config Service
- Logging Service
- (for the next phase) Notification Service


# Technologies

## Development Environment
- Build Tool: Maven
- Code Repository: GitHub
- IDE: Intellij IDEA

## Frameworks and Libraries (Dependencies)
- Spring Boot v3.1.4
- spring-cloud-starter-config
- spring-cloud-starter-netflix-eureka-client 
- spring-boot-starter-security
- spring-boot-starter-data-jpa
- org.postgresql
- spring-boot-devtools
- org.projectlombok

## Database
- Database System: PostgreSQL

## Caching
- (for the next phase) Employs Redis for caching frequently accessed data and session information.

# Config File
- Type: `YAML (yml)`
- Profile: `dev`, `prod`, `test`

## Local Config File
- `src/main/resources/application.yml`
```yml
server:
  port: 9010

spring:
  application:
    name: USER-SERVICE
  profiles:
    active: dev
  config:
    import: optional:configserver:http://localhost:9050
  cloud:
    config:
      enabled: true
```

## Remote Config File
- `https://github.com/jamongx/twitter-clone-config-server-repo/application-dev.yml`
```json
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:9040/eureka/
```

- `https://github.com/jamongx/twitter-clone-config-server-repo/user-service-dev.yml`
```yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/user_service_db
    username: admin_user
    password: 1234
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect
```

# User Story
## User Registration
- As a new user, I want to register so that I can create a new account and use the application.
### Tasks:
- Create a registration form.
- Implement backend logic to handle registration.
- Store user information securely.
### Acceptance Criteria:
- User can create a new account using an email, username, and password.
Passwords should be stored securely.
- A confirmation email is sent to the user.

## Log in & Log out
- As a registered user, I want to login and logout so that I can access and leave the application securely.
### Tasks:
- Create a login form.
- Implement backend logic to handle login and logout.
### Acceptance Criteria:
- Users can log in using their email/username and password.
- Users can logout.

## Password Reset
- As a user, I want to reset my password so that I can recover my account if I forget my password.
### Tasks:
- Create a password reset form.
- Implement backend logic to handle password resets.
### Acceptance Criteria:
- Users can request a password reset link to their email.
- Users can reset their password using the link.

## Follow/UnFollow
- As a user, I want to follow and unfollow other users so that I can receive their updates and manage my connections.
### Tasks:
- Implement the follow/unfollow feature.
- Update user’s follower and following count.
### Acceptance Criteria:
- Users can follow/unfollow other users.
- Users’ follower and following counts are updated correctly.


# Data Model
## Entities
- Detailed description of the entity classes and their relationships, including attributes and types.

## Database Schema
- Overview of the database schema, including tables, columns, types, and relationships.
```postgresql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL, -- Stored as a hashed value
    name VARCHAR(255),
    bio TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

```postgresql
CREATE TABLE follows (
    follower_id INTEGER REFERENCES users(id),
    followed_id INTEGER REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (follower_id, followed_id)
);
```

```postgresql
CREATE TABLE password_reset_tokens (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    token VARCHAR(255) UNIQUE NOT NULL,
    expiry TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

```postgresql
CREATE TABLE emails (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    email VARCHAR(255) NOT NULL,
    is_confirmed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

# API design
## API Endpoints
- Detailed specification of the API, including paths, methods, request/response bodies, status codes, and headers.

### User Registration
- Endpoint: /api/user/v1/register
- HTTP Method: POST
- Request Body:
```json
{
  "username": "sampleUser",
  "email": "sampleUser@example.com",
  "password": "samplePassword"
}
```
- Response:
```json
{
  "message": "Registration successful.",
  "user": {
    "id": 123,
    "username": "sampleUser",
    "email": "sampleUser@example.com"
  }
}
```
```json
{
  "error": "Username or email already exists."
}
```

### Login
- Endpoint: /api/user/v1/login
- HTTP Method: POST
- Request Body:
```json
{
    "email": "user@example.com",
    "username": "username",
    "password": "password"
}
```
- Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9....",
  "user": {
    "id": 123,
    "username": "sampleUser",
    ...other user info...
  }
}
```

```json
{
  "error": "Invalid username or password."
}
```

### Logout
- Endpoint: /api/user/v1/logout
- HTTP Method: POST
- Request Header:
```
Authorization: Bearer <token>
```
- Response:
```json
"message": "Logged out successfully."
```
```json
"error": "Invalid token."
```

### Password Reset
#### Password Reset Request
- Endpoint: /api/user/v1/password-reset/request
- HTTP Method: POST
- Request Body:
```json
{
  "email": "sampleUser@example.com"
}
```
- Response:
```json
{
  "message": "Password reset link sent to email."
}
```
```json
{
  "error": "Email not registered."
}
```

#### Token Verification
- Endpoint: /api/user/v1/password-reset/verify
- HTTP Method: POST
- Request Body:
```json
{
  "token": "abcd1234efgh5678"
}
```
- Response:
```json
{
  "message": "Token verified. Proceed to reset password."
}
```
```json
{
  "error": "Invalid or expired token."
}
```

#### Password Reset
- Endpoint: /api/user/v1/password-reset
- HTTP Method: POST
- Request Body:
```json
{
  "token": "abcd1234efgh5678",
  "newPassword": "newSamplePassword"
}
```
- Response:
```json
{
  "message": "Password reset successfully."
}
```
```json
{
  "error": "Invalid token or password format."
}
```
## API Authentication/Authorization
- Bearer Token (JWT).