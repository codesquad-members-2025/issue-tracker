package com.team5.issue_tracker.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
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
  // 따로 설정할 게 없다면 내용은 비워도 됩니다.
}

