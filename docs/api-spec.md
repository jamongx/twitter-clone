Let us do a basic API design for our services:

### Post a tweet
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

### Follow or unfollow a user
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

### Get newsfeed
This API will return all the tweets to be shown within a given newsfeed.
```
getNewsfeed(userID: UUID): Tweet[]
```
- Parameters
```
User ID (UUID): ID of the user.
```
- Returns
```
Tweets (Tweet []): All the tweets to be shown within a given newsfeed.
```
