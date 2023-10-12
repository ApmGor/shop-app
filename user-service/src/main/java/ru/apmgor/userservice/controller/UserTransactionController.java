package ru.apmgor.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.apmgor.userservice.dto.UserFullTransactionDto;
import ru.apmgor.userservice.dto.UserTransactionDto;
import ru.apmgor.userservice.service.UserTransactionService;

import static ru.apmgor.userservice.controller.UserUtil.USER_TRANSACTION_PATH;

@RestController
@RequestMapping(USER_TRANSACTION_PATH)
@RequiredArgsConstructor
public final class UserTransactionController {

    private final UserTransactionService service;

    @PostMapping
    public Mono<ResponseEntity<UserTransactionDto>> create(
            @RequestBody Mono<UserTransactionDto> dtoMono) {
        return dtoMono
                .flatMap(service::createTransaction)
                .map(ResponseEntity::ok);
    }

    @GetMapping()
    public Flux<UserFullTransactionDto> allUserTransactions(@RequestParam final Integer userId) {
        return service.getAllUserTransactions(userId);
    }
}
