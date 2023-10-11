package ru.apmgor.userservice.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ru.apmgor.userservice.entity.User;

public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

    @Modifying
    @Query("UPDATE users " +
            "SET balance = balance - :amount " +
            "WHERE id = :userId AND balance >= :amount")
    Mono<Boolean> updateUserBalance(final Integer userId, final Integer amount);
}
