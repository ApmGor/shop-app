package ru.apmgor.userservice.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Value
public class UserTransactionFullDto extends UserTransactionDto {
    Integer id;
    LocalDateTime transactionDate;

    public UserTransactionFullDto(
            final Integer id,
            final Integer userId,
            final Integer amount,
            final LocalDateTime transactionDate) {
        super(userId, amount);
        this.id = id;
        this.transactionDate = transactionDate;
    }
}
