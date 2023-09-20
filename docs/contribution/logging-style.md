
### Log Levels

- Fatal/Critical: These logs are generated when a severe error occurs that makes the service inoperable. Such errors often require immediate attention.
- Error: Use this level for issues that need to be addressed but don't render the entire service unusable. For example, failure to connect to a dependency could be logged at this level.
- Warning: This is used for abnormal but non-critical events that may signify a problem. Warnings may include configuration issues or the use of deprecated APIs.
- Info: These logs provide insights into the general behavior of the application, such as start-up and shutdown events, user authentication, and other significant state changes.
- Debug: Logs at this level provide detailed information for debugging purposes. These logs usually contain data that's useful for developers and operators but may be too verbose for everyday use in a production system.
- Trace: This level provides even more fine-grained logs than Debug and is often used to trace the step-by-step flow of a system. This level is usually only enabled during troubleshooting.


### Format

- Logs should be in JSON format for easy parsing.
- At the very least, logs need to contain the following details:

```
Service name
UserID
Hostname
Correlation ID (can be in the form of traceid)
RequestID
Time Stamp
Overall duration (at the end of a request)
Method name
Call stack (line number of the log)
Request Method
Request URI
```

### Example

```json
{
    "hostname": "baschidbs02-1-p.broadsoft.ims.comcast.net",
    "level": "info",
    "line": "order/order.go:48",
    "requestId": "42b2b58d-e9bb-482a-89cf-3a8ab3e3d027",
    "requestMethod": "POST",
    "message": "Successfully completed order request",
    "time":"2022-12-26T21:33:45",
    "requestPath": "/orders",
    "userAgent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36",
    "userId": "4538"
}
```


### AWS Services

- Use AWS CloudWatch for logging and monitoring.
- Consider using AWS X-Ray for tracing microservice calls.

### References

- https://signoz.io/blog/microservices-logging/