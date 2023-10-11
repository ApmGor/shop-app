package ru.apmgor.userservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.apmgor.userservice.entity.User;

public interface UserRepository extends ReactiveCrudRepository<User, Integer> {
}
