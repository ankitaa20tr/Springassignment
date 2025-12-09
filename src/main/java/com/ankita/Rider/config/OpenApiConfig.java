package com.ankita.Rider.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiDocs() {
        return new OpenAPI()
                .info(new Info()
                        .title("RideShare Backend API")
                        .description("Mini Project Backend for Ride Sharing System")
                        .version("1.0.0"));
    }
}