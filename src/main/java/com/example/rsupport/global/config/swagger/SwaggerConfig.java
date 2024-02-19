package com.example.rsupport.global.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        Server local = new Server()
                .description("로컬")
                .url("http://localhost:8180");

        return new OpenAPI()
                .info(new Info().title("Rsupport 사전과제 API")
                        .description("Rsupport 사전과제 API 명세")
                        .version("1.0.0")
                )
                .addServersItem(local);
    }

    @Bean
    public GroupedOpenApi noticeGroupApi() {
        return GroupedOpenApi.builder()
                .group("notice")
                .pathsToMatch("/api/notice/**")
                .build();
    }


    @Bean
    public GroupedOpenApi fileGroupApi() {
        return GroupedOpenApi.builder()
                .group("file")
                .pathsToMatch("/api/file/**")
                .build();
    }
}
