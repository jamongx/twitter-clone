 ### Reliability and Resilience

- Circuit Breakers: Use circuit breakers to handle failed requests gracefully.
- Retry Logic: Implement retry mechanisms to handle temporary network failures.
- Rate Limiting: Protect your services from being overwhelmed by too many requests.


### Resilient
- Running multiple instances of each of our services.
- Introducing [load balancers]() between clients, servers, databases, and cache servers.
- Using multiple read replicas for databases.
- Multiple instances and replicas for our distributed cache.
- Exactly once delivery and message ordering is challenging in a distributed system, we can use a dedicated [Message broker]() such as [Apache Kafka]() or [NATS]() to make our notification system more robust.
- We can add media processing and compression capabilities to the media service to compress large files which will save a lot of storage space and reduce cost.