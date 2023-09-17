
![](https://www.karanpratapsingh.com/_next/image?url=%2Fstatic%2Fcourses%2Fsystem-design%2Fchapter-V%2Ftwitter%2Ftwitter-datamodel.png&w=2048&q=75)


We have the following tables:
- users
This table will contain a user's information such as name, email, dob, and other details

- tweets
As the name suggests, this table will store tweets and their properties such as type (text, image video etc.), content, etc. We will also store the corresponding userID.

- favorites
This table maps tweets with users for the favorite tweets functionality in our application.

- followers
This table maps the followers and followees as users can follow each other (N:M relationship).

- feeds
This table stores feed properties with the corresponding userID.

- feeds tweets
This table maps tweets and feed (N:M relationship).


#### What kind of database should we use?
While our data model seems quite relational, we don't necessarily need to store everything in a single database, as this can limit our scalability and quickly become a bottleneck.
We will split the data between different services each having ownership over a particular table. Then we can use a relational database such as PostgreSQL or a distributed NoSQL database such as Apache Cassandra for our use case.
