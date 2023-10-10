package ru.apmgor.productservice.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.apmgor.productservice.entity.Product;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    @Query("{$and: [{price: {$gte: ?0}}, {price: {$lte: ?1}}]}")
    Flux<Product> findByPriceBetween(final int min, final int max);
}
