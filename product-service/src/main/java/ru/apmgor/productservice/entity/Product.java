package ru.apmgor.productservice.entity;

import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Document(collection = "products")
public class Product {
    @Id
    @With String id;
    String description;
    int price;
}
