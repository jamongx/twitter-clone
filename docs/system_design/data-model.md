- The data model being used includes several components such as database schemas and object models.
- Storing everything in a single database is not advisable, as it could limit scalability and quickly become a bottleneck.
- To address this, the data will be distributed across different services, each of which will have ownership over particular tables.
- Depending on use case, I opt for a relational database like PostgreSQL or a distributed NoSQL database like Apache Cassandra.


- Relational database management system (RDBMS) like PostgreSQL or MySQL can be a good choice
- These databases provide robust transactional capabilities and can handle structured data efficiently

### User Service
- Users: user_id (PK), username, email, hashed_password, bio, profile_image, created_at, etc.
- Followers: user_id (FK), follower_id (FK), followed_at, etc.
- Favorite: user_id (FK), follower_id (FK), followed_at, etc.

