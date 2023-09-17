- The service is responsible for handling search-related functionality. It will be discussed in detail separately.


### Database
- When it comes to search functionality, a search engine or indexing service like Elasticsearch (optimized for search operations) or Apache Solr can be valuable.
- These technologies are specifically designed for efficient searching and indexing of large volumes of data.
- Specialized in text-based search and analysis.
- They can provide fast and relevant search results based on various criteria, such as hashtags, user mentions, and content.


### Indices:
- UsersIndex: user_id, username, bio, etc. 
- TweetsIndex: tweet_id, content, timestamp, etc.


Sometimes traditional DBMS are not performant enough, we need something which allows us to store, search, and analyze huge volumes of data quickly and in near real-time and give results within milliseconds. Elasticsearch can help us with this use case.
Elasticsearch is a distributed, free and open search and analytics engine for all types of data, including textual, numerical, geospatial, structured, and unstructured. It is built on top of Apache Lucene.


### How do we identify trending topics?
Trending functionality will be based on top of the search functionality. We can cache the most frequently searched queries, hashtags, and topics in the last N seconds and update them every M seconds using some sort of batch job mechanism. Our ranking algorithm can also be applied to the trending topics to give them more weight and personalize them for the user.