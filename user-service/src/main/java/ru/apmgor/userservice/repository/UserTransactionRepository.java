package ru.apmgor.userservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.apmgor.userservice.entity.UserTransaction;

public interface UserTransactionRepository extends ReactiveCrudRepository<UserTransaction, Integer> {
}
