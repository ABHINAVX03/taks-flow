package com.taskflow.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title       = "TaskFlow REST API",
        version     = "v1",
        description = "Scalable Task Management API with JWT Authentication and Role-Based Access Control",
        contact     = @Contact(name = "Abhinav", email = "abhinav@taskflow.dev"),
        license     = @License(name = "MIT License")
    ),
    servers = {
        @Server(url = "/api/v1", description = "Development Server")
    }
)
@SecurityScheme(
    name         = "bearerAuth",
    type         = SecuritySchemeType.HTTP,
    scheme       = "bearer",
    bearerFormat = "JWT",
    description  = "Enter JWT token obtained from /auth/login"
)
public class OpenApiConfig {
    // Configuration is entirely annotation-driven
}
