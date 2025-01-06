# Chat Application Backend

> **🚧 ACTIVE DEVELOPMENT**: This project is currently under active development and should not be used in production.

> **💡 TECH SHOWCASE**: This project serves as a demonstration of modern Java backend development practices, architectural patterns, and cloud-native technologies. It showcases the integration of various cutting-edge tools and frameworks in a real-world application context.

A scalable chat application backend inspired by WeCom, built with Spring Boot and modern cloud-native technologies. This project exemplifies the implementation of:

- Domain-Driven Design with Spring Modulith
- Modern Java 21 features
- Clean architecture principles
- Containerization using Spring Boot's built-in support
- Real-time communication patterns
- Security best practices

## Features

### User Management
- Multi-tenant architecture supporting customers (organizations) and end users
- Complete CRUD operations for customer management by admin
- Customer-level user management capabilities
- Email-based user signup confirmation
- JWT-based authentication and authorization

### Chat Functionality
- Chat room management
- Real-time messaging using WebSocket STOMP protocol
- Support for multiple chat room types

### System Features
- API localization support
- Comprehensive API documentation with Swagger
- Modular and maintainable codebase using Spring Modulith
- Secure authentication and authorization with Spring Security

## Architecture

### Domain-Driven Design
The application follows Domain-Driven Design (DDD) principles to maintain a clean and business-focused architecture:

- **Bounded Contexts**: Clearly separated domains for admin, customer, user, and chat functionality
- **Ubiquitous Language**: Consistent terminology across codebase reflecting business domains
- **Aggregates**: Core domain objects (Customer, User, ChatRoom) with clear boundaries
- **Domain Events**: Using Spring Modulith for domain event publishing and handling
- **Repository Pattern**: Implemented via Spring Data JDBC
- **Value Objects**: Immutable objects representing domain concepts

### Module Structure
```
com.dd.direkt/
├── admin/                      # Admin management
│   ├── app/
│   │   ├── dto/               # Data transfer objects
│   │   ├── mapper/            # Object mappers
│   │   └── service/           # Application services
│   ├── domain/
│   │   ├── exception/         # Domain exceptions
│   │   └── repository/        # Domain repositories
│   └── webapi.v1/             # REST API controllers
├── customer/                   # Customer management
│   ├── app/
│   ├── domain/
│   └── webapi.v1/
├── email_service.internal/     # Internal email service
│   └── EmailService
├── shared_kernel/             # Shared components
│   ├── app/
│   ├── domain/
│   ├── infra/
│   ├── util/
│   └── package-info.java
└── user/                      # User management
    ├── app/
    ├── domain/
    ├── infra/
    └── webapi.v1/
```

## Technical Stack

### Core Technologies
- **Spring Boot**: Main application framework
- **Spring Data JDBC**: Database access layer
- **Spring Security**: Authentication and authorization
- **Spring Web MVC**: RESTful API development
- **Spring Modulith**: Application architecture and modularity

### Database & Storage
- **Supabase (PostgreSQL)**: Main database
- **MinIO**: File storage system (In Development)

### Messaging & Communication
- **WebSocket STOMP**: Real-time chat communication
- **RabbitMQ**: Message queue system (In Development)

### Monitoring & Observability
- **Prometheus**: Metrics collection
- **Grafana**: Metrics visualization and monitoring
(Both in development)

### Documentation
- **Swagger/OpenAPI**: API documentation and testing

### Deployment
- **Docker**: Containerization
- **Linux**: Deployment environment

## Configuration

### Base Configuration (application.yaml)
```yaml
server:
  port: 8081

spring:
  application:
    name: Direkt
  threads:
    virtual:
      enabled: true   # Project uses Virtual Threads (Java 21 feature)
  datasource:
    driver-class-name: org.postgresql.Driver
  sql:
    init:
      mode: always
      schema-locations: classpath:schema.sql
  modulith:
    republish-outstanding-events-on-restart: true
    events:
      jdbc:
        schema-initialization:
          enabled: true
```

### Environment-Specific Configurations

The project uses different configuration files for different environments:

#### Production (application-product.yaml)
```yaml
spring:
  config:
    import: classpath:secret.properties  # Sensitive data stored separately
  datasource:
    url: jdbc:postgresql://<supabase-url>:5432/postgres
    # Credentials loaded from secret.properties

app:
  url:
    confirm: ${CONFIRM_URL}  # Account confirmation URL
```

#### Development (application-dev.yaml)
```yaml
spring:
  config:
    import: classpath:secret.properties
  datasource:
    url: jdbc:postgresql://<supabase-url>:5432/postgres
    # Development credentials loaded from secret.properties

app:
  url:
    confirm: http://localhost:8081/api/v1/account/confirm
```

### Security Notes
- Sensitive information (database credentials, API keys) is stored in `secret.properties` (not included in repository)
- Different database users for production and staging environments
- Environment-specific configurations are activated using Spring profiles

## Getting Started

## Build Configuration

The project uses Gradle with modern configuration:

### Build Properties
- Java Version: 21
- Spring Boot: 3.x
- Project Group: com.dd
- Version: 0.0.2-ALPHA

### Key Dependencies
- **Spring Modulith**: Architectural boundary enforcement and event handling
- **MapStruct**: Object mapping between layers
- **Lombok**: Boilerplate code reduction
- **Spring Security**: Authentication and authorization
- **Spring Data JDBC**: Database access with DDD-friendly approach
- **JWT**: Token-based authentication
- **SpringDoc OpenAPI**: API documentation
- **WebSocket**: Real-time messaging support

### Container Support
The project currently uses Spring Boot's BuildImage task for basic containerization:
```gradle
tasks.named<BootBuildImage>("bootBuildImage") {
    imageName = "${dockerUsername}/direkt-messaging:${version}"
    publish = true
}
```

### Future Plans
- Kubernetes deployment configuration
- Service mesh integration
- Enhanced container orchestration
- Horizontal scaling support

### Prerequisites
- JDK 21
- Docker and Docker Compose
- PostgreSQL (via Supabase)
- Maven
- `secret.properties` file with required credentials

### Installation

1. Clone the repository
2. Create `secret.properties` with required database credentials:
```properties
sb.pd.user=<production-db-user>
sb.pd.pw=<production-db-password>
sb.stg.user=<staging-db-user>
sb.stg.pw=<staging-db-password>
```

3. Build the application:
```bash
./gradlew build
```

4. Run with specific profile:
```bash
java -jar build/libs/direkt-messaging-{version}.jar --spring.profiles.active=dev
```

### Configuration

Key configuration files:
- `application.yml`: Main application configuration
- `docker-compose.yml`: Docker services configuration

## API Documentation

Access the Swagger UI documentation at:
```
http://localhost:8080/swagger-ui.html
```

## Technology Demonstration

This project is designed to showcase several modern backend development approaches:

### Architectural Patterns
- **Clean Architecture**: Strict separation of concerns with layered architecture
- **DDD Implementation**: Real-world example of Domain-Driven Design
- **Event-Driven Architecture**: Using Spring Modulith for domain events
- **Modular Monolith**: Demonstrating how to build a well-structured monolith that can be split into microservices if needed

### Modern Development Practices
- **API Versioning**: Structured API versioning approach
- **Comprehensive Testing**: Unit, integration, and architectural testing
- **Infrastructure as Code**: Docker-based deployment
- **Observability**: Metrics, logging, and monitoring setup
- **Security Best Practices**: JWT-based authentication, proper authorization

### Developer Experience
- **API Documentation**: Auto-generated OpenAPI documentation
- **Development Tools**: Hot reload, development profiles
- **Code Quality**: Static analysis and code formatting
- **CI/CD Ready**: Docker build configuration

## Features in Development

- Message queueing with RabbitMQ
- File storage with MinIO
- Monitoring stack with Prometheus and Grafana
- Moving to microservice architecture and deployment with Kubernetes

## License

GPL-3.0 License
