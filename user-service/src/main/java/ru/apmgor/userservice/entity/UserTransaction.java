package ru.apmgor.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
@Table(value = "user_transactions")
public class UserTransaction {
    @Id
    Integer id;
    Integer userId;
    Integer amount;
    LocalDateTime transactionDate;
}
