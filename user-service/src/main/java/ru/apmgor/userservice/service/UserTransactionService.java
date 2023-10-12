package ru.apmgor.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.apmgor.userservice.dto.UserTransactionFullDto;
import ru.apmgor.userservice.dto.UserTransactionStatusDto;
import ru.apmgor.userservice.mapper.UserTransactionMapper;
import ru.apmgor.userservice.repository.UserRepository;
import ru.apmgor.userservice.repository.UserTransactionRepository;

import static ru.apmgor.userservice.dto.TransactionStatus.APPROVED;
import static ru.apmgor.userservice.dto.TransactionStatus.DECLINED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserTransactionService {

    private final UserTransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final UserTransactionMapper mapper;

    @Transactional
    public Mono<UserTransactionStatusDto> createTransaction(final UserTransactionStatusDto dto) {
        return userRepository.updateUserBalance(dto.getUserId(), dto.getAmount())
                .filter(Boolean::booleanValue)
                .map(bool -> mapper.toEntity(dto))
                .flatMap(transactionRepository::save)
                .map(mapper::toDto)
                .map(uDto -> uDto.withStatus(APPROVED))
                .defaultIfEmpty(dto.withStatus(DECLINED));
    }

    public Flux<UserTransactionFullDto> getAllUserTransactions(final Integer userId) {
        return transactionRepository.findAllByUserId(userId)
                .map(mapper::toFullDto);
    }
}
