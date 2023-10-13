package ru.apmgor.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.apmgor.orderservice.dto.OrderStatus;

@Data
@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private String productId;
    private Integer amount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Version
    private Integer version;
}
