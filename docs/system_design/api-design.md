- External communication
- Outlines available API endpoints and how to use them.
- It should specify the input/output format, error codes, etc., for each endpoint.


### RESTful API (HTTP/HTTPS Calls)

- Synchronous
- Description:
    - The service sends API requests to another service using the HTTP/HTTPS protocol and waits for a response.
- Reasons:
    - It's simple and intuitive.
    - Supported natively by many frameworks and languages.
    - Stateless communication allows for easy scaling out.
- Use Case: When the User Service requests a specific user's timeline from the Timeline Service.



### User service

#### User Registration and Authentication

#### Profile Management

#### Follow and Unfollow
- This API will allow the user to follow or unfollow another user.
```
follow(followerID: UUID, followeeID: UUID): boolean
unfollow(followerID: UUID, followeeID: UUID): boolean
```
- Parameters
```
Follower ID (UUID): ID of the current user.
Followee ID (UUID): ID of the user we want to follow or unfollow.
Media URL (string): URL of the attached media (optional).
```
- Returns
```
Result (boolean): Represents whether the operation was successful or not.
```