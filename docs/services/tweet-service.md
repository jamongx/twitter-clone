- The tweet service will handle tweet-related use cases such as posting a tweet, favorites, etc.

#### Database
- NoSQL (Cassandra): Due to the characteristics of a distributed system, it is possible to effectively handle large amounts of data.
- For tweet-related use cases, a combination of a relational database and a document-oriented database might be appropriate.
- The relational database can handle structured data associated with tweets, such as metadata,
- while a document-oriented database like MongoDB can store the actual tweet content, which can be flexible and schema-less.


#### Table:
- Tweets: tweet_id (PK), user_id (FK), content, timestamp, retweet_count, like_count ...
- TweetLikes: tweet_id (FK), user_id (FK), liked_at ...
- Likes: user_id (PK), tweet_id (composite key)
- Retweets: tweet_id (FK), user_id (FK), retweeted_at ...
- Retweets: user_id (PK), tweet_id (composite key), timestamp


#### tweets
- As the name suggests, this table will store tweets and their properties such as type (text, image video etc.), content, etc. We will also store the corresponding userID.


#### Retweets
- Retweets are one of our extended requirements.
- To implement this feature, we can simply create a new tweet with the user id of the user retweeting the original tweet and then modify the type enum and content property of the new tweet to link it with the original tweet.
- For example, the type enum property can be of type tweet, similar to text, video, etc and content can be the id of the original tweet. Here the first row indicates the original tweet while the second row is how we can represent a retweet.
- This is a very basic implementation. To improve this we can create a separate table itself to store retweets.

#### Post a tweet
This API will allow the user to post a tweet on the platform.
```
postTweet(userID: UUID, content: string, mediaURL?: string):
boolean
```
- Parameters
```
User ID (UUID): ID of the user.
Content (string): Contents of the tweet.
Media URL (string): URL of the attached media (optional).
```
- Returns
```
Result (boolean): Represents whether the operation was successful or not.
```
