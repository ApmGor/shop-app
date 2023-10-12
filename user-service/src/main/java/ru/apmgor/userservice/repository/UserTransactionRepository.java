package ru.apmgor.userservice.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.apmgor.userservice.entity.UserTransaction;

public interface UserTransactionRepository extends ReactiveCrudRepository<UserTransaction, Integer> {

    @Query("SELECT * FROM user_transactions WHERE user_id = :userId")
    Flux<UserTransaction> findAllByUserId(final Integer userId);
}
