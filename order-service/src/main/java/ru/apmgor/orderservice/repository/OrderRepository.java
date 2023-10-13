package ru.apmgor.orderservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.apmgor.orderservice.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Query(value = "SELECT * FROM orders WHERE user_id = :userId", nativeQuery = true)
    Iterable<Order> findOrderByUserId(final Integer userId);
}
