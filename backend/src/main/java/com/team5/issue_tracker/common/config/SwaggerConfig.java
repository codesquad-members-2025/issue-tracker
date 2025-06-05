package com.team5.issue_tracker.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Issue Tracker API", version = "v1"),
    servers = {
        @Server(url = "https://www.issue-tracker.online"),
        @Server(url = "https://issue-tracker.online"),
        @Server(url = "http://localhost:8080", description = "Local Development Server"),
        @Server(url = "https://localhost:8080", description = "Local Development Server with HTTPS")
    }
)
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPIWithSecurity() {
        // 1. Security Scheme 정의 (Bearer JWT)
        SecurityScheme bearerScheme = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .in(SecurityScheme.In.HEADER)
            .name("Authorization");

        // 2. 모든 API에 적용할 Security Requirement
        SecurityRequirement securityRequirement = new SecurityRequirement()
            .addList("bearerAuth");

        // 3. Components에 Security Scheme 등록 후, OpenAPI 객체에 설정
        return new OpenAPI()
            .components(new Components().addSecuritySchemes("bearerAuth", bearerScheme))
            .addSecurityItem(securityRequirement);
    }
}

