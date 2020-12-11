package com.webplusgd.aps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ApsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApsBackendApplication.class, args);
    }

}
