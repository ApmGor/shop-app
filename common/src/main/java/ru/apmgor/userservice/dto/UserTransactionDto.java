package ru.apmgor.userservice.dto;

public class UserTransactionDto {
    private final Integer userId;
    private final Integer amount;

    public UserTransactionDto(final Integer userId, final Integer amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getAmount() {
        return amount;
    }
}
