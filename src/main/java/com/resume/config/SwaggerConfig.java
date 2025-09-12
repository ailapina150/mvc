package com.resume.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
//@SecurityScheme(
//        name = "Bearer Authentication",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        scheme = "bearer"
//)
public class SwaggerConfig {
//    @Bean
//    public OpenAPI api() {
//        return new OpenAPI().
//                servers(
//                        List.of(
//                                new Server().url("http://localhost:8000")
//                        )
//                )
//                .info(
//                        new Info().title("API Resume").version("1.0")
//                );
//    }

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .servers(List.of(new Server().url("http://localhost:8000")))
                .info(new Info().title("API Resume").version("1.0"));
//                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
//               .components(new Components()
//                        .addSecuritySchemes("bearerAuth",
//                                new io.swagger.v3.oas.models.security.SecurityScheme()
//                                        .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT")));
    }
}
