package ru.apmgor.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import ru.apmgor.productservice.dto.ProductDto;

@Configuration
public class StreamConfig {

    @Bean
    public Sinks.Many<ProductDto> sink() {
        return Sinks.many().replay().limit(1);
    }

    @Bean
    public Flux<ProductDto> flux(final Sinks.Many<ProductDto> sink) {
        return sink.asFlux();
    }

}
