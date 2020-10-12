package com.webplusgd.aps.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Rollingegg
 * @create_time 10/12/2020 4:24 PM
 */

@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "aps")
public class ApsProperties {

    private boolean openAopLog = true;

    private String docsPath = "/api-docs";

    private SwaggerProperties swagger = new SwaggerProperties();
}
