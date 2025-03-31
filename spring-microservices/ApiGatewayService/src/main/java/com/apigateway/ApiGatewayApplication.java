package com.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {
    String personsServiceBaseUrlForLocalhost = "http://localhost:8081";
    String companiesServiceBaseurlForLocalhost = "http://localhost:8082";
    String personsServiceBaseUrlForDocker = "http://persons-service:8081";
    String companiesServiceBaseurlForDocker = "http://companies-service:8082";

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("persons-service", r -> r.path("/persons/**")
                        .uri(this.personsServiceBaseUrlForDocker))
                .route("companies-service", r -> r.path("/companies/**")
                        .uri(this.companiesServiceBaseurlForDocker))
                .build();
    }
}