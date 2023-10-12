package ru.apmgor.userservice.entity;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Value
@Builder
@Table(value = "users")
public class User {
    @Id
    @With
    Integer id;
    String name;
    Integer balance;
}
