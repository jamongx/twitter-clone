- This service will simply send push notifications to the users.

#### Database: NoSQL database + (Redis)
- For storing user notification preferences and history, a relational database can be utilized.
- RDBMS (for structured notifications data) + Redis (for real-time delivery)
        빠른 응답 시간이 필요한 작업에 적합


#### Table:
- Notifications: notification_id (PK), user_id (FK), type (like, retweet, follow), related_tweet_id, created_at, etc.
- Notifications: notification_id (PK), user_id (FK), content, timestamp, is_read


Push notifications are an integral part of any social media platform. We can use a message queue or a message broker such as Apache Kafka with the notification service to dispatch requests to Firebase Cloud Messaging (FCM) or Apple Push Notification Service (APNS) which will handle the delivery of the push notifications to user devices.
For more details, refer to the WhatsApp system design where we discuss push notifications in detail.
