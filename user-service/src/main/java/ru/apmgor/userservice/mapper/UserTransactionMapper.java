package ru.apmgor.userservice.mapper;

import org.springframework.stereotype.Component;
import ru.apmgor.userservice.dto.UserTransactionFullDto;
import ru.apmgor.userservice.dto.UserTransactionStatusDto;
import ru.apmgor.userservice.entity.UserTransaction;

import java.time.LocalDateTime;

@Component
public final class UserTransactionMapper implements Mapper<UserTransaction, UserTransactionStatusDto> {

    @Override
    public UserTransactionStatusDto toDto(final UserTransaction entity) {
        return new UserTransactionStatusDto(
                entity.getUserId(),
                entity.getAmount()
        );
    }

    public UserTransactionFullDto toFullDto(final UserTransaction entity) {
        return new UserTransactionFullDto(
                entity.getId(),
                entity.getUserId(),
                entity.getAmount(),
                entity.getTransactionDate()
        );
    }

    @Override
    public UserTransaction toEntity(final UserTransactionStatusDto dto) {
        return UserTransaction.builder()
                .userId(dto.getUserId())
                .amount(dto.getAmount())
                .transactionDate(LocalDateTime.now())
                .build();
    }
}
