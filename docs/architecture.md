
### High-level design
- Describes the main components of the system and how they interact with each other.
- Now let us do a high-level design of our system.

#### Architecture
We will be using microservices architecture since it will make it easier to horizontally scale and decouple our services.
Each service will have ownership of its own data model.
Let's try to divide our system into some core services.


#### User Service
- This service handles user-related concerns such as authentication and user information.
- Database
	- Relational database management system (RDBMS) like PostgreSQL or MySQL can be a good choice
	- These databases provide robust transactional capabilities and can handle structured data efficiently
- Table:
	- Users: user_id (PK), username, email, hashed_password, bio, profile_image, created_at 등
	- Followers: user_id (FK), follower_id (FK), followed_at 등


#### Timeline Service (Newsfeed Service)
- This service will handle the generation and publishing of user newsfeeds. It will be discussed in detail separately.
- Database
	- NoSQL 데이터베이스 (Cassandra)
	- To handle the generation and publishing of user newsfeeds, a combination of a caching layer and a database can be effective.
	- For caching, you can use solutions like Redis or Memcached, which provide high-speed data access.
	- As for the database, a NoSQL database like MongoDB or Cassandra can be suitable for storing and retrieving the newsfeed data.
- Table:
	- UserTimeline: user_id (FK), tweet_id (FK), timestamp
		- UserTimeline: user_id (PK), tweet_id (composite key), timestamp
	- HomeTimeline: user_id (FK), tweet_id (FK), timestamp
		- HomeTimeline: user_id (PK), tweet_id (composite key), timestamp


#### Tweet Service
- The tweet service will handle tweet-related use cases such as posting a tweet, favorites, etc.
- Database
	- NoSQL (Cassandra): 분산형 시스템의 특징으로 인해, 많은 양의 데이터를 효과적으로 처리 가능
	- For tweet-related use cases, a combination of a relational database and a document-oriented database might be appropriate.
	- The relational database can handle structured data associated with tweets, such as metadata,
	- while a document-oriented database like MongoDB can store the actual tweet content, which can be flexible and schema-less.
- Table:
	- Tweets: tweet_id (PK), user_id (FK), content, timestamp, retweet_count, like_count ...
	- TweetLikes: tweet_id (FK), user_id (FK), liked_at ...
		- Likes: user_id (PK), tweet_id (composite key)
	- Retweets: tweet_id (FK), user_id (FK), retweeted_at ...
		- Retweets: user_id (PK), tweet_id (composite key), timestamp


#### Search Service
- The service is responsible for handling search-related functionality. It will be discussed in detail separately.
- Database
	- When it comes to search functionality, a search engine or indexing service like Elasticsearch (optimized for search operations) or Apache Solr can be valuable.
	- These technologies are specifically designed for efficient searching and indexing of large volumes of data. 텍스트 기반 검색 및 분석에 특화되어 있음
	- They can provide fast and relevant search results based on various criteria, such as hashtags, user mentions, and content.
- Indices:
	- UsersIndex: user_id, username, bio 등
	- TweetsIndex: tweet_id, content, timestamp 등


#### Media service
- This service will handle the media (images, videos, files, etc.) uploads. It will be discussed in detail separately.
- Database
	- The metadata associated with the media can be stored in a relational or NoSQL database, depending on the requirements.
- Tables:
	- MediaMetadata: media_id (PK), user_id (FK), media_url, upload_timestamp, type (image/video), ...
- Storage:
	- For handling media uploads such as images, videos, and files, a popular approach is to use cloud storage services like Amazon S3 or Google Cloud Storage.
	- Blob storage (e.g., AWS S3) for storing media files


#### Notification Service
- This service will simply send push notifications to the users.
- Database: NoSQL 데이터베이스 + (Redis)
	- For storing user notification preferences and history, a relational database can be utilized.
	- RDBMS (for structured notifications data) + Redis (for real-time delivery)
	- 빠른 응답 시간이 필요한 작업에 적합
- Table:
	- Notifications: notification_id (PK), user_id (FK), type (like, retweet, follow), related_tweet_id, created_at 등
		- Notifications: notification_id (PK), user_id (FK), content, timestamp, is_read


#### Analytics Service
- This service will be used for metrics and analytics use cases.
- Database:
	- Meanwhile, for storing and analyzing large volumes of data for analytics purposes, a data warehousing solution like Apache Hadoop or Amazon Redshift can be employed.
	- Columnar storage databases for analytical processing, Amazon Redshift, BigQuery
- Tables/Views:
	- UserActivity: user_id (FK), tweet_id (FK), action_type (like/retweet/view), timestamp
	- TweetAnalytics: tweet_id (FK), views_count, likes_count, retweets_count, ...


#### Message Service
- Database
	- RDBMS (PostgreSQL, MySQL)
- Table:
	- Conversations: conversation_id (PK), user1_id (FK), user2_id (FK), last_message_timestamp
	- Messages: message_id (PK), conversation_id (FK), sender_id (FK), content, timestamp 등

