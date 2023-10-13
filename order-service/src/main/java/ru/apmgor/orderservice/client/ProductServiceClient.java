package ru.apmgor.orderservice.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.apmgor.productservice.dto.ProductDto;

@Component
public final class ProductServiceClient {

    private final WebClient webClient;

    public ProductServiceClient(@Qualifier("productClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ProductDto> getProductById(final String productId) {
        return webClient.get()
                .uri("/products/" + productId)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }
}
