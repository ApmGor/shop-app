package ru.apmgor.productservice.mapper;

import ru.apmgor.mapper.Mapper;
import ru.apmgor.productservice.dto.ProductDto;
import ru.apmgor.productservice.entity.Product;

public class ProductMapper implements Mapper<Product, ProductDto> {

    public ProductDto toDto(final Product product) {
        return new ProductDto(
                product.getId(),
                product.getDescription(),
                product.getPrice()
        );
    }

    public Product toEntity(final ProductDto dto) {
        return new Product(
                dto.id(),
                dto.description(),
                dto.price()
        );
    }
}
