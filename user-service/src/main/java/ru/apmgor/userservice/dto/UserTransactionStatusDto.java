package ru.apmgor.userservice.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

@EqualsAndHashCode(callSuper = true)
@Value
public class UserTransactionStatusDto extends UserTransactionDto {
    @With TransactionStatus status;

    public UserTransactionStatusDto(final Integer userId, final Integer amount) {
        super(userId, amount);
        this.status = null;
    }

    public UserTransactionStatusDto(final TransactionStatus status) {
        super();
        this.status = status;
    }
}
