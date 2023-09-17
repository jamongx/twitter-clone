It's time to discuss our design decisions in detail.

### Data Partitioning
To scale out our databases we will need to partition our data.
Horizontal partitioning (aka [[Concepts/System Design/Data Partitioning]]) can be a good first step.

We can use partitions schemes such as:
- Hash-Based Partitioning
- List-Based Partitioning
- Range Based Partitioning
- Composite Partitioning

The above approaches can still cause uneven data and load distribution, we can solve this using [[Consistent hashing]].

>For more details, refer to [[Concepts/System Design/Data Partitioning]] and [[Consistent Hashing]].


### Mutual friends
For mutual friends, we can build a social graph for every user.
- Each node in the graph will represent a user and a directional edge will represent followers and followees.
- After that, we can traverse the followers of a user to find and suggest a mutual friend.
- This would require a [[Graph Database|graph database]] such as [[Neo4j]] and [[ArangoDB]].

This is a pretty simple algorithm, to improve our suggestion accuracy, we will need to incorporate a recommendation model which uses machine learning as part of our algorithm.


### Metrics and Analytics
Recording analytics and metrics is one of our extended requirements. As we will be using [[Apache Kafka]] to publish all sorts of events, we can process these events and run analytics on the data using [[Apache Spark]] which is an open-source unified analytics engine for large-scale data processing.


### Caching
In a social media application, we have to be careful about using cache as our users expect the latest data. So, to prevent usage spikes from our resources we can cache the top 20% of the tweets.

To further improve efficiency we can add pagination to our system AP ls. This decision will be helpful for users with limited network bandwidth as they won't have to retrieve old messages unless requested.


##### Which cache eviction policy to use?
We can use solutions like [[Redis]] or [[Memcached]] and cache 20% of the daily traffic but what kind of cache eviction policy would best fit our needs?
[[Least Recently Used (LRU)]] can be a good policy for our system. In this policy, we discard the least recently used key first.


##### How to handle cache miss?
Whenever there is a cache miss, our servers can hit the database directly and update the cache with the new entries.
For more details, refer to [[Caching]].


#### Media access and storage
As we know, most of our storage space will be used for storing media files such as
images, videos, or other files. Our media service will be handling both access and
storage of the user media files.

But where can we store files at scale? Well, [[Object Storage|object storage]] is what we're looking for.
Object stores break data files up into pieces called objects. It then stores those
objects in a single repository, which can be spread out across multiple networked
systems. We can also use distributed file storage such as [[HDFS]] or [[GlusterFS]].


#### Content Delivery Network (CDN)
[[Content delivery network (CDN)]] increases content availability and redundancy while reducing bandwidth costs. Generally, static files such as images, and videos are served from CON. We can use services like [[Amazon CloudFront]] or [[Cloudflare CON]] for this use case.


#### Identify and resolve bottlenecks

![](https://www.karanpratapsingh.com/_next/image?url=%2Fstatic%2Fcourses%2Fsystem-design%2Fchapter-V%2Ftwitter%2Ftwitter-advanced-design.png&w=3840&q=75)

Let us identify and resolve bottlenecks such as single points of failure in our design:
- What if one of our services crashes?
- How will we distribute our traffic between our components?
- How can we reduce the load on our database?
- How to improve the availability of our cache?
- How can we make our notification system more robust?
- How can we reduce media storage costs?

To make our system more resilient we can do the following:
- Running multiple instances of each of our services.
- Introducing [[Mooc/System Design/System Design Interview - An Insider's guide/Load balancer|load balancers]] between clients, servers, databases, and cache servers.
- Using multiple read replicas for our databases.
- Multiple instances and replicas for our distributed cache.
- Exactly once delivery and message ordering is challenging in a distributed system, we can use a dedicated [[Message Brokers|message broker]] such as [[Apache Kafka]] or [[NATS]] to make our notification system more robust.
- We can add media processing and compression capabilities to the media service to compress large files which will save a lot of storage space and reduce cost.