### Requirements

#### Functional requirements
- Should be able to post new tweets (can be text, image, video, etc.).
- Should be able to follow other users.
- Should have a newsfeed feature consisting of tweets from the people the user is following.
- Should be able to search tweets.

#### Non-Functional requirements
- High availability with minimal latency.
- The system should be scalable and efficient.

#### Extended requirements
- Metrics and analytics.
- Retweet functionality.
- Favorite tweets.



### Back of the envelope estimation

#### Estimation and Constraints


#### Traffic
- This will be a read-heavy system,
	- let us assume we have 1 billion total users with 200 million daily active users (DAU),
	- and on average each user tweets 5 times a day
	- This gives us 1 billion tweets per day.

>200 million x 5 tweets = 1 billion/day (tweets)

- Tweets can also contain media such as images, or videos.
	- We can assume that 10 percent of tweets are media files shared by the users, which gives us additional 100 million files we would need to store.

>10 percent x 1 billion = 100 million/day

- What would be Requests Per Second (RPS) for our system?
	- 1 billion requests per day translate into 12K (12,000) requests per second.

>1 billion / (24 hrs x 3600 seconds) = ~12K requests/second


#### Storage
- If we assume each message on average is 100 bytes,
	- we will require about 100 GB of database storage every day.

>1 billion x 100 bytes = ~100 GB/day

- We also know that around 10 percent of our daily messages (100 million) are media files per our requirements.
	- If we assume each file is 50 KB on average, we will require 5 TB of storage every day.
	- and for 10 years, we will require about 19 PB of storage.

>100 million x 100 KB = 5 TB/day

>(5 TB + 0.1 TB) x 365 days x 10 years = ~19 PB


#### Bandwidth
- As our system is handling 5.1 TB of ingress every day, we will require a minimum bandwidth of around 60 MB per second.

>5.1 TB / (24 hrs x 3600 seconds) = ~60 MB/second


#### High-level estimate
- Daily active users (DAU): 200 million
- Each user 5 tweets per day on average
- Average tweet size 100 bytes:
	- tweet_id: 64 bytes
	- text: 100 bytes
	- media: 50 KB
- 10% of tweets contain media
- Requests per second (RPS): 12K/s
- Peek RPS = 2 * RPS = ~24K/s -> normally 2 or 3 times
- Storage (per day): ~5.1 TB
	- Tweets Storage: 0.1 TB
	- Media Storage: 5 TB
- Storage (10 years): ~19 PB
- Bandwidth: ~60 MB/s



### Technology Stack

#### Docker


#### Kubernetes


#### Spring boot


#### Postgresql


#### Apache kafka or rabbitmq


#### Apache casandra or mongodb



### High-level design

- Describes the main components of the system and how they interact with each other.

#### Architecture

- I will be using microservices architecture since it will make it easier to horizontally scale and decouple services.
- Each service will have ownership of its own data model.


#### [User Service](user-service.md)


#### [Tweet Service](tweet-service.md)


#### [Timeline Service](timeline-service.md)


#### [Search Service](search-service.md)


#### [Notification Service](notification-service.md)


#### [Media Service](media-service.md)


#### [Message Service](message-service.md)


#### [Ranking Service](ranking-service.md)


#### [Analytics Service](analytic-service.md)



### Data model design

- The data model being used includes several components such as database schemas and object models.
- I have several tables to store various types of data. (You can elaborate by listing or describing these tables.)
- Storing everything in a single database is not advisable, as it could limit scalability and quickly become a bottleneck.
- To address this, the data will be distributed across different services, each of which will have ownership over particular tables.
- Depending on use case, I opt for a relational database like PostgreSQL or a distributed NoSQL database like Apache Cassandra.



### External communication, API Documentation

- Outlines available API endpoints and how to use them.
- It should specify the input/output format, error codes, etc., for each endpoint.



#### RESTful API (HTTP/HTTPS Calls)
- Synchronous
- Description:
    - The service sends API requests to another service using the HTTP/HTTPS protocol and waits for a response.
- Reasons:
    - It's simple and intuitive.
    - Supported natively by many frameworks and languages.
    - Stateless communication allows for easy scaling out.
- Use Case: When the User Service requests a specific user's timeline from the Timeline Service.



### Inter-service communication

- Since my architecture is microservices-based, services will be communicating with each other as well.
- Generally, REST or HTTP performs well but we can further improve the performance using gRPC which is more lightweight and efficient.
- Service discovery is another thing we will have to take into account.
- I can also use a service mesh that enables managed, observable, and secure communication between individual services.

>Note: [REST, GraphQL, qRPC]()


#### Message Queues (RabbitMQ, Kafka)
- Asynchronous
- Description: The service adds messages to a queue, and another service receives and processes those messages.
- Reasons:
    - Enables decentralized communication between services.
    - Risk of data loss is low because messages remain in the queue even if the service is down.
    - Distributes load and ensures stable service operation, even during traffic peaks.
- Use Case: When a new tweet is created in the Tweet Service, data is added to the message queue to send to the Analytics Service.


#### Event-Driven (Message broker)
- Asynchronous
- Description: The service publishes specific events, and other services subscribe to and react to these events.
- Reasons:
    - Increases system flexibility and scalability through loose coupling between services.
    - Useful for real-time data or notification propagation.
    - Eases integration since all system components can use the same event stream.
- Use Case: When the User Service publishes an event for a user information change, the Notification Service subscribes to the event to send notifications to the user.



### Detailed design

It's time to discuss our design decisions in detail.

#### Data Partitioning
To scale out our databases we will need to partition our data.
Horizontal partitioning (aka [Data Partitioning]()) can be a good first step.

We can use partitions schemes such as:
- Hash-Based Partitioning
- List-Based Partitioning
- Range Based Partitioning
- Composite Partitioning

The above approaches can still cause uneven data and load distribution, we can solve this using [Consistent hashing]().

>For more details, refer to [Data Partitioning]() and [Consistent Hashing]().


#### Mutual friends
For mutual friends, we can build a social graph for every user.
- Each node in the graph will represent a user and a directional edge will represent followers and followees.
- After that, we can traverse the followers of a user to find and suggest a mutual friend.
- This would require a [[Graph Database|graph database]] such as [Neo4j]() and [[ArangoDB]().

This is a pretty simple algorithm, to improve our suggestion accuracy, we will need to incorporate a recommendation model which uses machine learning as part of our algorithm.


#### Metrics and Analytics
Recording analytics and metrics is one of our extended requirements. As we will be using [Apache Kafka]() to publish all sorts of events, we can process these events and run analytics on the data using [Apache Spark]() which is an open-source unified analytics engine for large-scale data processing.


#### Caching
In a social media application, we have to be careful about using cache as our users expect the latest data. So, to prevent usage spikes from our resources we can cache the top 20% of the tweets.

To further improve efficiency we can add pagination to our system AP ls. This decision will be helpful for users with limited network bandwidth as they won't have to retrieve old messages unless requested.


##### Which cache eviction policy to use?
We can use solutions like [Redis]() or [Memcached]() and cache 20% of the daily traffic but what kind of cache eviction policy would best fit our needs?
[Least Recently Used (LRU)]() can be a good policy for our system. In this policy, we discard the least recently used key first.


##### How to handle cache miss?
Whenever there is a cache miss, our servers can hit the database directly and update the cache with the new entries.
For more details, refer to [Caching]().


#### Media access and storage
As we know, most of our storage space will be used for storing media files such as
images, videos, or other files. Our media service will be handling both access and
storage of the user media files.

But where can we store files at scale? Well, [Object storage]() is what we're looking for.
Object stores break data files up into pieces called objects. It then stores those
objects in a single repository, which can be spread out across multiple networked
systems. We can also use distributed file storage such as [HDFS]() or [GlusterFS]().


#### Content Delivery Network (CDN)
- [Content delivery network (CDN)]() increases content availability and redundancy while reducing bandwidth costs.
- Generally, static files such as images, and videos are served from CON.
- We can use services like [Amazon CloudFront]() or [Cloudflare CON]() for this use case.



### Identify and resolve bottlenecks

- Identify and resolve bottlenecks such as single points of failure in our design:
    - What if one of our services crashes?
    - How will we distribute our traffic between our components?
    - How can we reduce the load on our database?
    - How to improve the availability of our cache?
    - How can we make our notification system more robust?
    - How can we reduce media storage costs?


### Resilient
- Running multiple instances of each of our services.
- Introducing [load balancers]() between clients, servers, databases, and cache servers.
- Using multiple read replicas for databases.
- Multiple instances and replicas for our distributed cache.
- Exactly once delivery and message ordering is challenging in a distributed system, we can use a dedicated [Message broker]() such as [Apache Kafka]() or [NATS]() to make our notification system more robust.
- We can add media processing and compression capabilities to the media service to compress large files which will save a lot of storage space and reduce cost.



### References
- System Design Interview - An Insider’s Guide
- [Karan Pratap Singh - System Design (2022)](https://www.karanpratapsingh.com/courses/system-design/twitter)