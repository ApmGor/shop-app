package ru.apmgor.userservice.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserFullTransactionDto(
        Integer id,
        Integer userId,
        Integer amount,
        LocalDateTime transactionDate
) {}
