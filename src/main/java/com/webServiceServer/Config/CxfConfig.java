package com.webServiceServer.Config;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
@EnableAutoConfiguration
public class CxfConfig {

    @Bean
    public ServletRegistrationBean createServletRegistrationBean() {

        return new ServletRegistrationBean(new CXFServlet(), "/ws/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

//    @Bean
//    OrderPort orderPort(){return new OrderPortImpl();}


//    @Bean(name = "orderPortEndpoint")
//    public Endpoint endpoint1() {
//        EndpointImpl endpoint = new EndpointImpl(springBus(), orderPort());
//        endpoint.publish("/order");
//        return endpoint;
//    }


}
