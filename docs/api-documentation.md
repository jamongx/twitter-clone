
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