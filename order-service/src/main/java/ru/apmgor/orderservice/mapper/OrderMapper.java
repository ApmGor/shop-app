package ru.apmgor.orderservice.mapper;

import org.springframework.stereotype.Component;
import ru.apmgor.mapper.Mapper;
import ru.apmgor.orderservice.dto.OrderResponseDto;
import ru.apmgor.orderservice.entity.Order;

@Component
public class OrderMapper implements Mapper<Order, OrderResponseDto> {

    @Override
    public OrderResponseDto toDto(final Order entity) {
        return new OrderResponseDto(
                entity.getUserId(),
                entity.getProductId(),
                entity.getId(),
                entity.getAmount(),
                entity.getStatus()
        );
    }

    @Override
    public Order toEntity(final OrderResponseDto dto) {
        return new Order(
                dto.getOrderId(),
                dto.getUserId(),
                dto.getProductId(),
                dto.getAmount(),
                dto.getStatus(),
                null
        );
    }
}
