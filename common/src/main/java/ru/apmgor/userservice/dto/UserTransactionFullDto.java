package ru.apmgor.userservice.dto;

import java.time.LocalDateTime;

public class UserTransactionFullDto extends UserTransactionDto {
    private final Integer id;
    private final LocalDateTime transactionDate;

    public UserTransactionFullDto(
            final Integer id,
            final Integer userId,
            final Integer amount,
            final LocalDateTime transactionDate) {
        super(userId, amount);
        this.id = id;
        this.transactionDate = transactionDate;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
}
