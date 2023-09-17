#### Database:
- RDBMS (PostgreSQL, MySQL)


#### Table:
- Conversations: conversation_id (PK), user1_id (FK), user2_id (FK), last_message_timestamp
- Messages: message_id (PK), conversation_id (FK), sender_id (FK), content, timestamp, extra