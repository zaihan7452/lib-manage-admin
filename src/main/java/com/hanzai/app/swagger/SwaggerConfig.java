package com.hanzai.app.swagger;

import com.hanzai.app.constant.LibManageAdminConstant;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = HttpHeaders.AUTHORIZATION;

        return new OpenAPI()
                .info(new Info()
                        .title("Lib Manage Admin API")
                        .version("v0.0.1")
                        .description("Lib Manage Admin API Documentation")
                        .contact(new Contact().name("Han Zai").email("zaihan7452@gmail.com")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme(LibManageAdminConstant.BEARER)
                                .bearerFormat(LibManageAdminConstant.JWT)));
    }

}
