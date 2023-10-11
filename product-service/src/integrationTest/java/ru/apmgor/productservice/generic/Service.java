package ru.apmgor.productservice.generic;

public record Service(
        String name,
        int port,
        String resourcePath,
        String containerPath,
        String uri
) {}
