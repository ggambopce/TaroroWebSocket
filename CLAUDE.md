# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun

# Run all tests
./gradlew test

# Run a single test class
./gradlew test --tests "com.neocompany.torowebsocketserver.TorowebsocketserverApplicationTests"

# Run a single test method
./gradlew test --tests "com.neocompany.torowebsocketserver.SomeTest.methodName"

# Clean build
./gradlew clean build

# Skip tests during build
./gradlew build -x test
```

## Architecture Overview

Spring Boot 3.5.11 WebSocket server targeting Java 17. Group: `com.neocompany`, base package: `com.neocompany.torowebsocketserver`.

### Core Stack
- **Spring Web** - REST APIs
- **Spring WebSocket** - WebSocket communication
- **Spring Security** - Auth with OAuth2 (Google, Kakao, Naver)
- **Spring Data JPA + MySQL** - Persistence (Hibernate, DDL auto: update)
- **Redis** - Caching (localhost:6379)
- **Lombok** - Boilerplate reduction (use `@Data`, `@Builder`, etc.)

### External Integrations
- **OAuth2:** Google, Kakao, Naver (credentials in `application.yml`, which is gitignored)
- **Email:** Gmail SMTP
- **Payment:** TOSS payment gateway (test keys)
- **API Docs:** SpringDoc/Swagger at `/api/swagger` (JSON at `/api/api-docs`)

### Configuration
`application.yml` is gitignored — each developer maintains their own local copy. Database is MySQL at `localhost:3306`, database name `spring-mysql`, timezone `Asia/Seoul`. Hikari pool: max 10, min 2.

### Package Conventions
Follow standard Spring layering under `com.neocompany.torowebsocketserver`:
- `controller/` — REST and WebSocket controllers
- `service/` — Business logic
- `repository/` — JPA repositories
- `entity/` — JPA entities
- `config/` — Spring configuration classes (Security, WebSocket, etc.)
- `dto/` — Request/Response DTOs
