package com.webplusgd.aps;

import com.webplusgd.aps.webServiceClient.WSImport2DB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@SpringBootApplication
@EnableScheduling
public class ApsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApsBackendApplication.class, args);
    }

}
