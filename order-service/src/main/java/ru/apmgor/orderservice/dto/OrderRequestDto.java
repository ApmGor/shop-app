package ru.apmgor.orderservice.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class OrderRequestDto extends OrderDto {

    public OrderRequestDto(
            final Integer userId,
            final String productId
    ) {
        super(userId, productId);
    }
}
