package ru.apmgor.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.apmgor.userservice.dto.UserTransactionFullDto;
import ru.apmgor.userservice.dto.UserTransactionStatusDto;
import ru.apmgor.userservice.service.UserTransactionService;

import static ru.apmgor.userservice.controller.UserUtil.USER_TRANSACTION_PATH;

@RestController
@RequestMapping(USER_TRANSACTION_PATH)
@RequiredArgsConstructor
public final class UserTransactionController {

    private final UserTransactionService service;

    @PostMapping
    public Mono<ResponseEntity<UserTransactionStatusDto>> create(
            @RequestBody Mono<UserTransactionStatusDto> dtoMono) {
        return dtoMono
                .flatMap(service::createTransaction)
                .map(ResponseEntity::ok);
    }

    @GetMapping()
    public Flux<UserTransactionFullDto> allUserTransactions(@RequestParam final Integer userId) {
        return service.getAllUserTransactions(userId);
    }
}
