package ru.apmgor.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import ru.apmgor.orderservice.client.ProductServiceClient;
import ru.apmgor.orderservice.client.UserServiceClient;
import ru.apmgor.orderservice.dto.OrderRequestDto;
import ru.apmgor.orderservice.dto.OrderResponseDto;
import ru.apmgor.orderservice.dto.OrderStatus;
import ru.apmgor.orderservice.mapper.OrderMapper;
import ru.apmgor.orderservice.repository.OrderRepository;
import ru.apmgor.productservice.dto.ProductDto;
import ru.apmgor.userservice.dto.TransactionStatus;
import ru.apmgor.userservice.dto.UserTransactionDto;
import ru.apmgor.userservice.dto.UserTransactionStatusDto;

import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public final class OrderService {

    private final ProductServiceClient productClient;
    private final UserServiceClient serviceClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;

    public Mono<OrderResponseDto> createOrder(final Mono<OrderRequestDto> shareMono) {
        return shareMono
                .map(OrderRequestDto::getProductId)
                .flatMap(productClient::getProductById)
                .zipWith(shareMono)
                .map(this::createUTSDtoFrom)
                .flatMap(serviceClient::createUserTransaction)
                .zipWith(shareMono)
                .map(this::createORespDtoFrom)
                .map(mapper::toEntity)
                .publishOn(Schedulers.boundedElastic())
                .map(repository::save) // блокирующий вызов, нужно выполнять в фоновом потоке
                .map(mapper::toDto);
    }

    public Flux<OrderResponseDto> getAllUserOrders(final Integer userId) {
        return Flux.fromStream(() -> StreamSupport
                        .stream(repository.findOrderByUserId(userId).spliterator(), false))
                .subscribeOn(Schedulers.boundedElastic())
                .map(mapper::toDto);
    }

    private OrderResponseDto createORespDtoFrom(final Tuple2<UserTransactionStatusDto, OrderRequestDto> tuple) {
        return new OrderResponseDto(
                tuple.getT2().getUserId(),
                tuple.getT2().getProductId(),
                tuple.getT1().getAmount(),
                getOrderStatusBy(tuple.getT1().getStatus())
        );
    }

    private UserTransactionDto createUTSDtoFrom(final Tuple2<ProductDto, OrderRequestDto> tuple) {
        return new UserTransactionDto(tuple.getT2().getUserId(), tuple.getT1().price());
    }

    private OrderStatus getOrderStatusBy(final TransactionStatus status) {
        return switch (status) {
            case APPROVED -> OrderStatus.COMPLETED;
            case DECLINED -> OrderStatus.FAILED;
        };
    }
}
