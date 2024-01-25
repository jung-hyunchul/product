package com.homework.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {
    @Server(url = "/", description = "Default Server URL")}
)
@RequiredArgsConstructor
public class OpenApiConfig {

  @Bean
  public OpenAPI openApi() {
    return new OpenAPI()
        .info(
            new Info()
                .title("product api")
                .description("product api"));
  }

  @Bean
  public GroupedOpenApi apiGroupedV1() {
    return GroupedOpenApi.builder().group("1. api v1")
        .pathsToMatch("/api/v1/**")
        .build();
  }
}
