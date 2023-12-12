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

### Users Table

The `users` table stores the core user information. Below is the schema for the `users` table:
- Indexes: 
  - Primary Key: `id`
  - Unique Constraint on `email`
- Referenced by:
  - `user_profiles` table (Foreign Key: `id` references `users(id)`)
  - `user_profiles` table (Foreign Key: `user_id` references `users(id)`)

| Column     | Type                             | Description                  |
|------------|----------------------------------|------------------------------|
| id         | bigint                           | Primary key, auto-increment  |
| username   | character varying(255)           | Username, unique             |
| email      | character varying(255)           | User email, unique           |
| password   | character varying(255)           | Hashed password              |
| created_at | timestamp(6) without time zone   | Account creation timestamp   |
| updated_at | timestamp(6) without time zone   | Last account update timestamp|


### User Profiles Table
The `user_profiles` table contains additional information for users. Below is the schema for the `user_profiles` table:

| Column       | Type                             | Description                      |
|--------------|----------------------------------|----------------------------------|
| id           | bigint                           | Primary key                      |
| active       | boolean                          | User's active status             |
| avatar_url   | character varying(255)           | URL for user's avatar            |
| display_name | character varying(255)           | User's display name              |
| bio          | character varying(255)           | User biography                   |
| birth_date   | date                             | User's date of birth             |
| created_at   | timestamp(6) without time zone   | Account creation timestamp       |
| updated_at   | timestamp(6) without time zone   | Last profile update timestamp    |
| user_id      | bigint                           | Reference to user ID             |


### Roles Table

The `roles` table manages user roles within the application. Below is the schema for the `roles` table:
- Indexes: 
  - Primary Key: `id`
  - Unique Constraint on `role`
- Referenced by:
  - `users_roles` table (Foreign Key: `role_id` references `roles(id)`)


| Column     | Type                             | Description                  |
|------------|----------------------------------|------------------------------|
| id         | bigint                           | Primary key, auto-increment  |
| created_at | timestamp(6) without time zone   | Role creation timestamp      |
| role       | character varying(255)           | Unique role name             |


### Users Roles Table

The `users_roles` table links users with their respective roles. Below is the schema for the `users_roles` table:

| Column  | Type   | Description                |
|---------|--------|----------------------------|
| user_id | bigint | Foreign key to user's ID   |
| role_id | bigint | Foreign key to role's ID   |

- Indexes:
  - Primary Key: `(user_id, role_id)`
- Foreign-key constraints:
  - `user_id` references `user_profiles(id)`
  - `role_id` references `roles(id)`




# API design
## API Endpoints
- Detailed specification of the API, including paths, methods, request/response bodies, status codes, and headers.

## Authentication Service

### User Registration
- **Endpoint**: `/api/v1/auth/register`
- **HTTP Method**: `POST`
- **Request Body**:
```json
{
  "username": "sampleUsername",
  "password": "samplePassword",
  "displayName": "Sample User",
  "email": "sample@example.com",
  "bio": "User biography",
  "birthDate": "1990-01-01"
}
```

- **Response**: HTTP 201 (Created):
```json
{
  "message": "User Registered Successfully!."
}
```

- **Response**: HTTP 400 (Bad Request):
```json
{
  "error": "Username already exists!"
}
```
```json
{
  "error": "Email already exists!"
}
```

### Login
- **Endpoint**: `/api/v1/auth/login`
- **HTTP Method**: `POST`
- **Request Body**:
```json
{
  "username": "Username",
  "password": "Password"
}
```
```json
{
  "username": "Email",
  "password": "Password"
}
```

- **Response**: HTTP 200 (OK):
```json
{
  "accessToken": "yourAccessTokenString",
  "tokenType": "Bearer",
  "id": 12345,
  "avatarUrl": "http://example.com/avatar.jpg",
  "username": "sampleUser",
  "email": "sampleUser@example.com",
  "displayName": "Sample User",
  "bio": "User biography here",
  "active": true,
  "createdAt": "2023-01-01T12:00:00",
  "birthDate": "1990-01-01",
  "roles": [
    {
      "roleName": "ROLE_USER"
    },
    {
      "roleName": "ROLE_ADMIN"
    }
  ]
}
```
- **Response**: HTTP 404 (Not Found):
```json
{
  "error": "User not found with username or email : exampleUserOrEmail"
}
```

## User Service

### Get User
- **Endpoint**: /api/v1/users/{id}
- **HTTP Method**: GET
- **Path Variable**: id - User Profile ID
- **Response**: HTTP 200 (OK)
```json
{
  "id": 1,
  "username": "user1",
  "email": "user1@example.com"
}
```

### Get All Users
- **Endpoint**: /api/v1/users
- **HTTP Method**: GET
- **Response**: HTTP 200 (OK)
```json
[
  {
    "id": 1,
    "username": "user1",
    "email": "user1@example.com"
  },
  {
    "id": 2,
    "username": "user2",
    "email": "user2@example.com"
  },
  {
    "id": 3,
    "username": "user3",
    "email": "user3@example.com"
  }
]
```

### Add User
- **Endpoint**: /api/v1/users
- **HTTP Method**: POST
- **Request Body**:
```json
{
  "id": 0, // not defined
  "username": "user1",
  "email": "user1@example.com"
}
```
- **Response**: HTTP 201 (Created)
```json
{
  "id": 1, // new generated
  "username": "user1",
  "email": "user1@example.com"
}
```
- **Response**: HTTP 400 (Bad Request)



### Update User
- **Endpoint**: /api/v1/users/{id}
- **HTTP Method**: PUT
- **Path Variable**: id - User ID
- **Request Body**:
```json
{
  "id": 1,
  "username": "updated_username",
  "email": "updated_username@example.com"
}
```
- **Response**: HTTP 200 (OK)
```json
{
  "id": 1,
  "username": "updated_username",
  "email": "updated_username@example.com"
}
```
- **Response**: HTTP 404 (Not Found)


### Delete User
- **Endpoint**: /api/v1/users/{id}
- **HTTP Method**: DELETE
- **Path Variable**: id - User Profile ID
- **Response**: HTTP 204 (No Content)



## User Profile Service

### Update User Profile status (active/deactive)
- **Endpoint**: /api/v1/user_profiles/{id}/status
- **HTTP Method**: PATCH
- **Path Variable**: id - User Profile ID
- **Response**: HTTP 200 (OK)
```json
{
  "active": true/false
}
```
- **Response**: HTTP 404 (Not Found)
```json
{
  "error": "UserProfile not found with id : 1234"
}
```

### Update Avatar (Profile Photo)
- **Endpoint**: /api/v1/user_profiles/{id}/avatar
- **HTTP Method**: PATCH
- **Path Variable**: id - User Profile ID
- **Form Data**: file - MultipartFile (Avatar image)
- **Response**: HTTP 200 (OK)
```json
{
  "avatarUrl": "http://example.com/avatar.jpg"
}
```
- **Response**: HTTP 400 (Bad Request)
```json
{
  "error": "File is empty"
}
```
- **Response**: HTTP 404 (Not Found):
```json
{
  "error": "UserProfile not found with id : 1234"
}
```


### Update User Profile
- **Endpoint**: /api/v1/user_profiles/{id}
- **HTTP Method**: PUT
- **Path Variable**: id - User Profile ID
- **Request Body**:
```json
{
  "id": 123,
  "avatarUrl": "http://example.com/avatar.jpg",
  "displayName": "Sample User",
  "bio": "User biography here",
  "active": true,
  "birthDate": "1990-01-01"
}
```
- **Response**: HTTP 200 (OK)
```json
{
  "id": 123,
  "avatarUrl": "http://example.com/avatar.jpg",
  "displayName": "Sample User",
  "bio": "User biography here",
  "active": true,
  "birthDate": "1990-01-01"
}
```
- **Response**: HTTP 404 (Not Found):
```json
{
  "error": "UserProfile not found with id : 1234"
}
```


## Follow Service

### Get Following
- **Endpoint**: /api/v1/follows/{id}/following
- **HTTP Method**: GET
- **Path Variable**: id - User ID
- **Response**: HTTP 200 (OK)
```json
[
  {
    "followersId": 1,
    "followingId": 2
  },
  {
    "followersId": 1,
    "followingId": 3
  },
  {
    "followersId": 1,
    "followingId": 4
  }
]
```

### Get Followers
- **Endpoint**: /api/v1/follows/{id}/followers
- **HTTP Method**: GET
- **Path Variable**: id - User ID
- **Response**: HTTP 200 (OK)
```json
[
  {
    "followersId": 5,
    "followingId": 3
  },
  {
    "followersId": 6,
    "followingId": 3
  },
  {
    "followersId": 7,
    "followingId": 3
  }
]
```

### Add Follow
- **Endpoint**: /api/v1/follows
- **HTTP Method**: POST
- **Request Body**:
```json
{
  "followersId": 1,
  "followingId": 2
}
```
- **Response**: HTTP 201 (Created)
```json
{
  "followersId": 1,
  "followingId": 2
}
```
- **Response**: HTTP 400 (Bad Request):
```json
{
  "error": "Username already exists!"
}
```


### Delete Follow
- **Endpoint**: /api/v1/follows/{id}
- **HTTP Method**: DELETE
- **Path Variable**: id - Follow ID
- **Response**: HTTP 204 (No Content)




### Password Reset
#### Password Reset Request