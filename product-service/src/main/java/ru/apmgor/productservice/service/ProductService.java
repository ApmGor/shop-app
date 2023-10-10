package ru.apmgor.productservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.apmgor.productservice.dto.ProductDto;
import ru.apmgor.productservice.entity.Product;
import ru.apmgor.productservice.mapper.ProductMapper;
import ru.apmgor.productservice.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Flux<ProductDto> getAllProducts() {
        return repository.findAll()
                .map(ProductMapper::toDto);
    }

    public Mono<ProductDto> getOneProduct(final String id) {
        return repository.findById(id)
                .map(ProductMapper::toDto);
    }

    public Mono<String> saveProduct(final Mono<ProductDto> dtoMono) {
        return dtoMono
                .map(ProductMapper::toEntity)
                .flatMap(repository::insert)
                .map(Product::getId);
    }

    public Mono<ProductDto> updateProduct(final String id, final Mono<ProductDto> dtoMono) {
        return repository.findById(id)
                .flatMap(product -> converter(id, dtoMono))
                .flatMap(repository::save)
                .map(ProductMapper::toDto);
    }

    private Mono<Product> converter(final String id, final Mono<ProductDto> dtoMono) {
        return dtoMono
                .map(ProductMapper::toEntity)
                .map(product -> product.withId(id));
    }

    public Mono<Void> deleteProduct(final String id) {
        return repository.deleteById(id);
    }

}













