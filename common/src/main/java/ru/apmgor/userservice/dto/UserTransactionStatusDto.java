package ru.apmgor.userservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTransactionStatusDto extends UserTransactionDto {
    private final TransactionStatus status;

    @JsonCreator
    public UserTransactionStatusDto(
            @JsonProperty("userId") final Integer userId,
            @JsonProperty("amount") final Integer amount,
            @JsonProperty("status") final TransactionStatus status
    ) {
        super(userId, amount);
        this.status = status;
    }

    public UserTransactionStatusDto(final Integer userId, final Integer amount) {
        this(userId, amount, null);
    }

    public UserTransactionStatusDto withStatus(final TransactionStatus status) {
        return new UserTransactionStatusDto(this.getUserId(), this.getAmount(), status);
    }

    public TransactionStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "UserTransactionStatusDto{" +
                "userId=" + getUserId() +
                ", amount=" + getAmount() +
                ", status=" + status +
                '}';
    }
}
