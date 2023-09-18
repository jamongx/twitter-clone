
- Relational database management system (RDBMS) like PostgreSQL or MySQL can be a good choice
- These databases provide robust transactional capabilities and can handle structured data efficiently

### User Service
- Users: user_id (PK), username, email, hashed_password, bio, profile_image, created_at, etc.
- Followers: user_id (FK), follower_id (FK), followed_at, etc.
- Favorite: user_id (FK), follower_id (FK), followed_at, etc.

