package ru.apmgor.userservice.dto;

import lombok.Builder;

@Builder
public record UserDto(
        int id,
        String name,
        int balance
) {}
