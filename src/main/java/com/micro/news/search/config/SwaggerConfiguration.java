package com.micro.news.search.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfiguration {

    @Bean
    public OpenAPI apiInfo() {

        return new OpenAPI()
                .addServersItem(
                        new Server()
                                .description("News Search API")
                )
                .info(
                        new Info()
                                .title("News Search")
                                .description("API description of News Search Service")
                                .version("1.0.0"));
    }
}
