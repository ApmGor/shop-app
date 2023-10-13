package ru.apmgor.orderservice.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class OrderResponseDto extends OrderDto {
    Integer orderId;
    Integer amount;
    OrderStatus status;
}
