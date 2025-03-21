package com.example.subsavvy.Security;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Configuration
    public class SwaggerConfig {

        private SecurityScheme createAPIKeyScheme() {
            return new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .bearerFormat("JWT")
                    .scheme("bearer");
        }

        @Bean
        public OpenAPI openAPI() {
            return new OpenAPI().addSecurityItem(new SecurityRequirement().
                            addList("Bearer Authentication"))
                    .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                    .info(new Info().title("SubSavvy API")
                            .description("Documentation for the SubSavvy     API.")
                            .version("0.1"));
        }

    }
}
