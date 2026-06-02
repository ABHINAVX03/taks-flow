# TaskFlow API

Spring Boot backend for TaskFlow.

## Overview

This repository contains the Java Spring Boot API for TaskFlow, including authentication, user management, and task CRUD operations.

## Features

- JWT-based authentication
- Role-based access control
- PostgreSQL / Supabase production support
- H2 in-memory database for local development
- Profile-based configuration: `dev` and `prod`
- OpenAPI / Swagger support in development
- Docker-ready backend image

## Requirements

- Java 17
- Maven 3.8+
- Docker (optional for containerized deployment)

## Environment

Create a file named `.env` or set these variables in your deployment environment.

```env
SPRING_PROFILES_ACTIVE=dev
SPRING_DATASOURCE_URL=jdbc:h2:mem:taskflowdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
SPRING_DATASOURCE_USERNAME=sa
SPRING_DATASOURCE_PASSWORD=
JWT_SECRET=change_this_secret_in_development
JWT_EXPIRATION_MS=86400000
CORS_ALLOWED_ORIGINS=http://localhost:5173
SPRING_SQL_INIT_MODE=always
```

For production with Supabase or PostgreSQL:

```env
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=jdbc:postgresql://<HOST>:5432/<DBNAME>?sslmode=require
SPRING_DATASOURCE_USERNAME=<DB_USER>
SPRING_DATASOURCE_PASSWORD=<DB_PASSWORD>
JWT_SECRET=<secure_jwt_secret>
JWT_EXPIRATION_MS=86400000
CORS_ALLOWED_ORIGINS=https://<YOUR_FRONTEND_DOMAIN>
SPRING_SQL_INIT_MODE=never
```

## Profiles

- `dev`: uses H2 database, enables OpenAPI/Swagger, and updates schema automatically.
- `prod`: uses PostgreSQL, validates schema, and disables Swagger/OpenAPI.

## Running Locally

### Development

```bash
mvn clean spring-boot:run -Dspring-boot.run.profiles=dev
```

### Production Profile Locally

```bash
mvn clean spring-boot:run -Dspring-boot.run.profiles=prod
```

## Docker

Build the backend image:

```bash
docker build -t taskflow-api .
```

Run with environment variables:

```bash
docker run -e SPRING_PROFILES_ACTIVE=prod \
  -e SPRING_DATASOURCE_URL="jdbc:postgresql://<HOST>:5432/<DBNAME>?sslmode=require" \
  -e SPRING_DATASOURCE_USERNAME=<DB_USER> \
  -e SPRING_DATASOURCE_PASSWORD=<DB_PASSWORD> \
  -e JWT_SECRET=<secure_jwt_secret> \
  -p 8080:8080 taskflow-api
```

## API Base Path

The API is served under `/api/v1`.

- Login: `POST /api/v1/auth/login`
- Register: `POST /api/v1/auth/register`

## Notes

- Ensure `JWT_SECRET` is strong in production.
- Use `SPRING_PROFILES_ACTIVE=prod` when deploying to Railway or another cloud provider.
- The backend expects the frontend origin in `CORS_ALLOWED_ORIGINS`.
