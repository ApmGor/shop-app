package ru.apmgor.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.apmgor.orderservice.dto.OrderRequestDto;
import ru.apmgor.orderservice.dto.OrderResponseDto;
import ru.apmgor.orderservice.service.OrderService;

import static ru.apmgor.orderservice.controller.OrderUtil.ORDER_PATH;

@RestController
@RequestMapping(ORDER_PATH)
@RequiredArgsConstructor
public final class OrderController {

    private final OrderService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<OrderResponseDto> order(@RequestBody final Mono<OrderRequestDto> dtoMono) {
        return service.createOrder(dtoMono.share());
    }
}
