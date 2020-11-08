package com.webplusgd.aps.config;

import com.webplusgd.aps.properties.ApsProperties;
import com.webplusgd.aps.properties.SwaggerProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springdoc.core.Constants.SWAGGER_UI_PATH;

/**
 * @author Rollingegg
 * @create_time 10/12/2020 4:52 PM
 * 配置swagger
 */
@Configuration
public class ApsConfig implements WebMvcConfigurer {
    private final ApsProperties apsProperties;

    public ApsConfig(ApsProperties apsProperties) {
        this.apsProperties = apsProperties;
    }

    @Bean
    @Profile("!prod")
    public GroupedOpenApi actuatorApi() {
        return GroupedOpenApi.builder().group("Actuator")
                .pathsToMatch("/actuator/**")
                .pathsToExclude("/actuator/health/*")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        SwaggerProperties swagger = apsProperties.getSwagger();
        Contact contact = new Contact();
        contact.setName(swagger.getAuthor());
        contact.setUrl(swagger.getUrl());
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("basicScheme",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
                .info(new Info().title(swagger.getTitle()).version(swagger.getVersion())
                        .description(swagger.getDescription())
                        .contact(contact))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringDoc Full Documentation")
                        .url("https://springdoc.org/")
                );
    }


    @Value(SWAGGER_UI_PATH)
    private String swaggerUiPath;

    /**
     * 将swagger-ui重定向到指定路径
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController(apsProperties.getDocsPath(), swaggerUiPath);
    }

}
