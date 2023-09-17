
### Estimation and Constraints

Let's start with the estimation and constraints.
Note: Make sure to check any scale or traffic-related assumptions with your interviewer.

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