package ru.apmgor.productservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.apmgor.productservice.entity.Product;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
