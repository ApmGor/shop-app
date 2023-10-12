package ru.apmgor.userservice.generic;

public record Service(
        String name,
        int port,
        String resourcePath,
        String containerPath,
        String uri
) {}
