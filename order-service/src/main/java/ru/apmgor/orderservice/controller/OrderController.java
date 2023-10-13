package ru.apmgor.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.apmgor.orderservice.dto.OrderRequestDto;
import ru.apmgor.orderservice.dto.OrderResponseDto;
import ru.apmgor.orderservice.service.OrderService;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;
import static ru.apmgor.orderservice.controller.OrderUtil.ORDER_PATH;

@RestController
@RequestMapping(ORDER_PATH)
@RequiredArgsConstructor
public final class OrderController {

    private final OrderService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<OrderResponseDto>> order(@RequestBody final Mono<OrderRequestDto> dtoMono) {
        return service.createOrder(dtoMono.share())
                .map(ResponseEntity::ok)
                .onErrorReturn(WebClientResponseException.class, badRequest().build())
                .onErrorReturn(WebClientRequestException.class, status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @GetMapping("/users/{userId}")
    public Flux<OrderResponseDto> allUserOrders(@PathVariable final Integer userId) {
        return service.getAllUserOrders(userId);
    }
}
