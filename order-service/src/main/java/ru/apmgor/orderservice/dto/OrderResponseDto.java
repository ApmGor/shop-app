package ru.apmgor.orderservice.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class OrderResponseDto extends OrderDto {
    Integer orderId;
    Integer amount;
    OrderStatus status;

    public OrderResponseDto(
            final Integer userId,
            final String productId,
            final Integer orderId,
            final Integer amount,
            final OrderStatus status
    ) {
        super(userId, productId);
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
    }

    public OrderResponseDto(
            final Integer userId,
            final String productId,
            final Integer amount,
            final OrderStatus status
    ) {
        this(userId, productId, null, amount, status);
    }
}
