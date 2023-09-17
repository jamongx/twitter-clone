As we discussed, we will need a ranking algorithm to rank each tweet according to its relevance to each specific user.
For example, Facebook used to utilize an EdgeRank algorithm. Here, the rank of each feed item is described by:

```
Rank = Affinity X Weight X Decay
```

Where,
- Affinity: is the "closeness" of the user to the creator of the edge. If a user frequently likes, comments, or messages the edge creator, then the value of affinity will be higher, resulting in a higher rank for the post.
- Weight: is the value assigned according to each edge. A comment can have a higher weightage than likes, and thus a post with more comments is more likely to get a higher rank.
- Decay: is the measure of the creation of the edge. The older the edge, the lesser will be the value of decay and eventually the rank.
- Nowadays, algorithms are much more complex and ranking is done using machine learning models which can take thousands of factors into consideration.


#### Retweets
Retweets are one of our extended requirements. To implement this feature, we can simply create a new tweet with the user id of the user retweeting the original tweet and then modify the type enum and content property of the new tweet to link it with the original tweet.
For example, the type enum property can be of type tweet, similar to text, video, etc and content can be the id of the original tweet. Here the first row indicates the original tweet while the second row is how we can represent a retweet.

![](https://i.imgur.com/k8HMPPr.png)

This is a very basic implementation. To improve this we can create a separate table itself to store retweets.