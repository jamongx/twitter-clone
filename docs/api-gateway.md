Deciding whether to use only Spring Cloud Gateway or to also integrate AWS API Gateway depends on various factors.
Here are some considerations for each approach:

### Advantages of Using Only Spring Cloud Gateway:
1. Integration: It naturally integrates with Spring Boot, making it easier to configure in a Java application.
2. Customization: You have more flexibility to customize routing logic and other features at the application level.
3. Ease of Local Testing: Testing in a local environment may be simpler.

### Advantages of Using AWS API Gateway Alongside:
1. Security: AWS API Gateway can enhance security through integration with other AWS services like AWS Cognito.
2. Scaling: Utilizing AWS infrastructure makes features like auto-scaling and failover easier to implement.
3. Monitoring and Logging: It integrates with AWS CloudWatch for robust monitoring and logging.

### Conclusion:
- If simplicity and integration are important, using only Spring Cloud Gateway could be advantageous.
- If cloud-level features like security, scaling, and monitoring are critical, then integrating AWS API Gateway could be worth considering.

Therefore, the choice between the two will be influenced by your project's requirements, your team's tech stack, and other considerations like cost.