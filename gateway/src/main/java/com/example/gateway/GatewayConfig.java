package com.example.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("product-service", r -> r
                        .path("/api/products/**")
                        .uri("http://product-service:8081"))
                .route("user-service", r -> r
                        .path("/api/users/**")
                        .uri("http://user-service:8082"))
                .route("order-service", r -> r
                        .path("/api/orders/**", "/api/cart/**")
                        .uri("http://order-service:8083"))
                .build();
    }
}
