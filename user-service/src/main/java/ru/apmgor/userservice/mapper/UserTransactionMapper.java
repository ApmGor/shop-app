package ru.apmgor.userservice.mapper;

import org.springframework.stereotype.Component;
import ru.apmgor.userservice.dto.UserTransactionDto;
import ru.apmgor.userservice.entity.UserTransaction;

import java.time.LocalDateTime;

@Component
public final class UserTransactionMapper implements Mapper<UserTransaction, UserTransactionDto> {

    @Override
    public UserTransactionDto toDto(final UserTransaction entity) {
        return UserTransactionDto.builder()
                .userId(entity.getUserId())
                .amount(entity.getAmount())
                .build();
    }

    @Override
    public UserTransaction toEntity(final UserTransactionDto dto) {
        return UserTransaction.builder()
                .userId(dto.userId())
                .amount(dto.amount())
                .transactionDate(LocalDateTime.now())
                .build();
    }
}
