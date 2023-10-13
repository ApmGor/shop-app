package ru.apmgor.orderservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.apmgor.orderservice.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
