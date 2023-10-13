package ru.apmgor.orderservice.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.apmgor.userservice.dto.UserTransactionDto;
import ru.apmgor.userservice.dto.UserTransactionStatusDto;

@Component
public final class UserServiceClient {

    private final WebClient userServiceClient;

    public UserServiceClient(@Qualifier("userClient") final WebClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    public Mono<UserTransactionStatusDto> createUserTransaction(final UserTransactionDto dto) {
        return userServiceClient.post()
                .uri("/users/transactions")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(UserTransactionStatusDto.class);
    }
}
