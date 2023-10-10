package ru.apmgor.productservice.dto;

public record ProductDto(
        String id,
        String description,
        int price
) {}
