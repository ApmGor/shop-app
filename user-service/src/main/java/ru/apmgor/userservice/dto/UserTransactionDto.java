package ru.apmgor.userservice.dto;

import lombok.Builder;
import lombok.With;

@Builder
public record UserTransactionDto(
        Integer userId,
        Integer amount,
        @With TransactionStatus status
) {}
