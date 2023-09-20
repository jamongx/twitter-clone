### Inter-service communication

- Since my architecture is microservices-based, services will be communicating with each other as well.
- Generally, REST or HTTP performs well but we can further improve the performance using gRPC which is more lightweight and efficient.
- Service discovery is another thing we will have to take into account.
- I can also use a service mesh that enables managed, observable, and secure communication between individual services.

```
Note: [REST, GraphQL, qRPC]()
```


#### Message Queues (RabbitMQ, Kafka)
- Asynchronous
- Description: The service adds messages to a queue, and another service receives and processes those messages.
- Reasons:
    - Enables decentralized communication between services.
    - Risk of data loss is low because messages remain in the queue even if the service is down.
    - Distributes load and ensures stable service operation, even during traffic peaks.
- Use Case: When a new tweet is created in the Tweet Service, data is added to the message queue to send to the Analytics Service.


#### Event-Driven (Message broker)
- Asynchronous
- Description: The service publishes specific events, and other services subscribe to and react to these events.
- Reasons:
    - Increases system flexibility and scalability through loose coupling between services.
    - Useful for real-time data or notification propagation.
    - Eases integration since all system components can use the same event stream.
- Use Case: When the User Service publishes an event for a user information change, the Notification Service subscribes to the event to send notifications to the user.

