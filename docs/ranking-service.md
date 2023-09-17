#### Ranking Algorithm

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


