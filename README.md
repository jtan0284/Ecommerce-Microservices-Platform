![Blank diagram](https://github.com/user-attachments/assets/2cc37d5e-2250-4394-a0ef-2316e4f57b30)
# Ecommerce Microservices Project Demo
An e-commerce backend built with Spring Boot microservices, API Gateway, centralized configuration, JWT authentication with Keycloak, asynchronous messaging with RabbitMQ, and observability using Prometheus, Loki, and Grafana. The system is deployed on Kubernetes.

## Architecture
- **Gateway Service** — single entry point for API routing and JWT validation
- **User Service** — manages users in MongoDB and integrates with Keycloak Admin API
- **Product Service** — manages product catalog in PostgreSQL
- **Order Service** — manages cart and orders in PostgreSQL
- **Notification Service** — consumes order events from RabbitMQ
- **Config Server** — centralizes externalized configuration
- **Keycloak** — authentication and authorization provider
- **RabbitMQ / CloudAMQP** — asynchronous messaging broker
- **Prometheus** — metrics collection
- **Loki + Alloy + Grafana** — log aggregation and visualization

## System Flow
1. Client authenticates with Keycloak
2. Keycloak issues a JWT access token
3. Client sends requests to Gateway with `Authorization: Bearer <token>`
4. Gateway validates the token against Keycloak
5. If valid, the request is forwarded to the target microservice
