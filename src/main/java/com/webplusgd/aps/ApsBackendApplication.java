package com.webplusgd.aps;

import com.webplusgd.aps.webServiceClient.WSImport2DB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class ApsBackendApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ApsBackendApplication.class, args);

//        WSImport2DB wsImport2DB = (WSImport2DB)context.getBean("WSImport2DB");
//        wsImport2DB.wsImport();
    }

}
