- This service handles user-related concerns such as authentication and user information.
- This table will contain a user's information such as name, email, dob, and other details
- This table maps the followers and followees as users can follow each other (N:M relationship).
- This table maps tweets with users for the favorite tweets functionality in our application.

#### Database

- Relational database management system (RDBMS) like PostgreSQL or MySQL can be a good choice
- These databases provide robust transactional capabilities and can handle structured data efficiently

#### Table:
- Users: user_id (PK), username, email, hashed_password, bio, profile_image, created_at, etc.
- Followers: user_id (FK), follower_id (FK), followed_at, etc.
- Favorite: user_id (FK), follower_id (FK), followed_at, etc.



#### Follow or unfollow a user
This API will allow the user to follow or unfollow another user.
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