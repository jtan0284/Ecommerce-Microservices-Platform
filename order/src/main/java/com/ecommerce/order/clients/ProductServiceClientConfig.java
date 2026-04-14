package com.ecommerce.order.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Optional;

@Configuration
public class ProductServiceClientConfig {
    @Bean
    public ProductServiceClient productServiceInterface(RestClient.Builder restClientBuilder, @Value("${product.service.url}") String productServiceUrl){
        RestClient restClient = restClientBuilder
                .baseUrl(productServiceUrl)
                .defaultStatusHandler(HttpStatusCode::is4xxClientError,
                        ((request, response) -> Optional.empty()))
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(adapter)
                .build();
        ProductServiceClient productServiceClient = factory.createClient(ProductServiceClient.class);
        return productServiceClient;
    }
}
