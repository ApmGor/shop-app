package ru.apmgor.productservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import ru.apmgor.productservice.dto.ProductDto;
import ru.apmgor.productservice.entity.Product;
import ru.apmgor.productservice.mapper.ProductMapper;
import ru.apmgor.productservice.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public final class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final Sinks.Many<ProductDto> sink;

    public Flux<ProductDto> getAllProducts() {
        return repository.findAll()
                .map(mapper::toDto);
    }

    public Mono<ProductDto> getOneProduct(final String id) {
        return repository.findById(id)
                .map(mapper::toDto);
    }

    public Mono<String> saveProduct(final Mono<ProductDto> dtoMono) {
        return dtoMono
                .map(mapper::toEntity)
                .flatMap(repository::insert)
                .map(mapper::toDto)
                .doOnNext(sink::tryEmitNext)
                .map(ProductDto::id);
    }

    public Mono<ProductDto> updateProduct(final String id, final Mono<ProductDto> dtoMono) {
        return repository.findById(id)
                .flatMap(product -> converter(id, dtoMono))
                .flatMap(repository::save)
                .map(mapper::toDto);
    }

    private Mono<Product> converter(final String id, final Mono<ProductDto> dtoMono) {
        return dtoMono
                .map(mapper::toEntity)
                .map(product -> product.withId(id));
    }

    public Mono<Void> deleteProduct(final String id) {
        return repository.deleteById(id);
    }

    public Flux<ProductDto> getPriceBetween(final int min, final int max) {
        return repository.findByPriceBetween(min, max)
                .map(mapper::toDto);
    }

}













