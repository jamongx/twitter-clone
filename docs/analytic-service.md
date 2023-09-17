- This service will be used for metrics and analytics use cases.


#### Database:
    - Meanwhile, for storing and analyzing large volumes of data for analytics purposes, a data warehousing solution like Apache Hadoop or Amazon Redshift can be employed.
    - Columnar storage databases for analytical processing, Amazon Redshift, BigQuery


#### Tables/Views:
    - UserActivity: user_id (FK), tweet_id (FK), action_type (like/retweet/view), timestamp
    - TweetAnalytics: tweet_id (FK), views_count, likes_count, retweets_count, ...