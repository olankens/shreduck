package com.example.shreduck.il.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfig {

    public OpenApiConfig(MappingJackson2HttpMessageConverter converter) {
        List<MediaType> supportMediaTypes = new ArrayList<>(converter.getSupportedMediaTypes());
        supportMediaTypes.add(new MediaType("application", "octet-stream"));
        supportMediaTypes.add(new MediaType("application", "json"));
        converter.setSupportedMediaTypes(supportMediaTypes);
    }

    @Bean
    public OpenAPI openAPI() {
        final String securitySchemeName = "BearerAuth";
        return new OpenAPI()
                .addSecurityItem(
                        new SecurityRequirement().addList(securitySchemeName)
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }

}