package com.project.inventoryservice.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger API documentation.
 */

@Configuration
public class SwaggerConfig {

    /**
     * Creates a custom OpenAPI configuration for the API documentation.
     *
     * @return the custom OpenAPI configuration
     */

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info()
                        .title("ZMCare API")
                        .description("ZMCare is a comprehensive platform designed to facilitate caregivers in managing the " +
                                "vaccination-related data of their dependents. Caregivers can securely link their dependents " +
                                "to the app and access essential information such as vaccination schedules, past vaccination " +
                                "records, and personalized reminders. ZMCare aims to streamline the vaccination process, " +
                                "ensuring timely and efficient healthcare management for dependents.")
                        .version("1.0.0")
                        .contact(new Contact().name("ZMCare Development Team")
                                .email("softwareteam@ird.global").url("https://ird.global"))
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

}
