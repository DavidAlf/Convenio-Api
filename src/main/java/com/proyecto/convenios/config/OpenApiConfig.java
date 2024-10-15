package com.proyecto.convenios.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.Generated;

@Generated
@Configuration
@OpenAPIDefinition(info = @Info(title = "CONVENIOS-API", version = "0.0.1", description = "Aplicacion para manejo de convenios"))
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/v1/convenios/**") 
                .build();
    }
}
