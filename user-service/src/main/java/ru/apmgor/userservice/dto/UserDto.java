package ru.apmgor.userservice.dto;

import lombok.Builder;

@Builder
public record UserDto(
        Integer id,
        String name,
        Integer balance
) {}
