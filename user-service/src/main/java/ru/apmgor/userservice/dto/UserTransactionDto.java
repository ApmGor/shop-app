package ru.apmgor.userservice.dto;

import java.time.LocalDateTime;

public record UserTransactionDto(
        Integer id,
        Integer userId,
        Integer amount,
        LocalDateTime transactionDate,
        TransactionStatus status
) {
    public enum TransactionStatus {
        APPROVED, DECLINED
    }
}
