package ru.apmgor.orderservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    @Value("${services.product.url}")
    private String productUrl;

    @Value("${services.user.url}")
    private String userUrl;

    @Bean
    public WebClient productClient() {
        return WebClient.builder()
                .baseUrl(productUrl)
                .build();
    }

    @Bean
    public WebClient userClient() {
        return WebClient.builder()
                .baseUrl(userUrl)
                .build();
    }
}
