package com.modsen.bookservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
        name = "token",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer"
)
@OpenAPIDefinition(
        info = @Info(
                title = "Book Service API",
                description = "Book Service", version = "1.0.0",
                contact = @Contact(
                        name = "Danila Lisichkin",
                        email = "lisichkindanila@gmail.com"
                )
        )
)
@Configuration
public class OpenApiConfig {

}
