package ru.apmgor.orderservice.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import ru.apmgor.productservice.dto.ProductDto;

import java.time.Duration;

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
                .bodyToMono(ProductDto.class)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)));
    }

    public Flux<ProductDto> getAllProducts() {
        return webClient.get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(ProductDto.class);
    }
}
