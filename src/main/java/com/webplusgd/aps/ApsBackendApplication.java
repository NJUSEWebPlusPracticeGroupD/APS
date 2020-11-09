package com.webplusgd.aps;

import com.webplusgd.aps.webServiceClient.WSImport2DB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApsBackendApplication.class, args);
        WSImport2DB wsImport2DB = new WSImport2DB();
        wsImport2DB.wsImport();
    }

}
