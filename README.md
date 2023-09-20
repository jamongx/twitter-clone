### Twitter (Clone)
- Personal project, Twitter clone that is based on a microservices architecture


### Considerations
- There are several considerations to ensure that the system in a microservices architecture is robust, scalable, and maintainable.

- [Design and Decoupling](docs/considerations/design-and-decoupling.md)
- [Data Management](docs/considerations/data-management.md)
- [Communication](docs/considerations/communications.md)
- [Scalability and Performance](docs/considerations/scalability.md)
- [Deployment and Orchestration](docs/considerations/deployment.md)
- [Logging](docs/considerations/logging.md)
- [Monitoring](docs/considerations/monitoring.md)
- [Security](docs/considerations/security.md)
- [Documentation and Governance](docs/considerations/documentation.md)
- [Identify and resolve bottlenecks](docs/considerations/bottlenecks.md)
- [Reliability and Resilience](docs/considerations/resilience.md)


### System Design
- High level design and architecture of the system.

- [Requirements](docs/system_design/requirement.md)
- [Back of the envelope estimation](docs/system_design/back-of-the-envelope-estimation.md)
- [Technical stack](docs/system_design/technical-stack.md)
- [High-level design](docs/system_design/high-level-design.md)
- [Data model design](docs/system_design/data-model.md)
- [External communication, API design](docs/system_design/api-design.md)
- [Inter-service communication](docs/system_design/inter-comm.md)
- [Detailed design](docs/system_design/detailed-design.md)


#### Build CI/CD pipeline
- Build CI/CD pipeline on AWS, using Ansible, Jenkins, GitHub, Docker, Kubernetes and SonarQube.
- This includes required software, environment variable settings, database configurations, etc.

- [Basic Preparations](docs/cicd/basic-prparations.md)
- [AWS Configuration](docs/cicd/aws.md)
- [Git Repository](docs/cicd/git-repository.md)
- [Install Jenkins](docs/cicd/jenkins.md)
- [Install Ansible](docs/cicd/ansible.md)
- [Jenkins & Git Integration](docs/cicd/jenkins-git-integration.md)
- [Configure Jenkins Pipeline](docs/cicd/config-jenkins-pipeline.md)
- [Write a Dockerfile](docs/cicd/write-dockerfile.md)
- [Trigger Build](docs/cicd/trigger-build.md)
- [Run Tests](docs/cicd/run-tests.md)
- [Docker Build](docs/cicd/docker-build.md)
- [Push to Docker Registry](docs/cicd/push-docker-registry.md)
- [Write Ansible Playbook](docs/cicd/write-ansible-playbook.md)
- [Run Ansible Playbook](docs/cicd/run-ansible-playbook.md)
- [Create Kubernetes YAML Files](docs/cicd/kubernetes-yaml-files.md)
- [Kubernetes Deployment](docs/cicd/kubernetes-deployment.md)
- [Monitoring and Logging](docs/cicd/monitoring-logging.md)
- [Documentation](docs/cicd/documentation.md)


### Development Plan
- Develop the services including database schemas, API endpoints, and class diagrams according to the plan.


#### Phase 1
- [Gateway Service](docs/gateway-service.md)
- [Service Discovery](docs/service-discovery.md)
- [User Service](docs/user-service.md)


#### Phase 2
- [Tweet Service](docs/tweet-service.md)
- [Media Service](docs/media-service.md)
- [Timeline Service](docs/timeline-service.md)
- [Notification Service](docs/notification-service.md)
- [Search Service](docs/search-service.md)
- [Message Service](docs/message-service.md)
- [Ranking Service](docs/ranking-service.md)
- [Analytics Service](docs/analytic-service.md)


### [Test Plan](docs/test-plan.md)
- Specifies the testing strategy and individual test cases.


### Contribution Guidelines
- [Getting Started](docs/contribution/getting-started.md)
- [Java Code Style Guide](docs/contribution/java-code-style-guide.md).
- [Logging Style](docs/contribution/logging-style.md)
- [Submitting Changes](docs/contribution/submitting-changes.md)
- [Branching Strategy](docs/contribution/branching-strategy.md)
- [Code Reviews](docs/contribution/code-reviews.md)
- [Issue Tracking](docs/contribution/issue-tracking.md)
- [Testing](docs/contribution/testing.md)


### References
- [System Design Interview - An Insider’s Guide](https://www.amazon.ca/System-Design-Interview-insiders-Second/dp/B08CMF2CQF)
- [Karan Pratap Singh - System Design (2022)](https://www.karanpratapsingh.com/courses/system-design/twitter)


### History
- [2023-09-19, Jenkins CI/CD Pipeline - SonarQube, Docker, Github Webhooks on AWS](docs/history/2023-09-19.md)

