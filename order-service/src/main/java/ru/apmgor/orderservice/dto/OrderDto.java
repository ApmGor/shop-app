package ru.apmgor.orderservice.dto;

import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public class OrderDto {
    Integer userId;
    String productId;
}
