package ru.apmgor.orderservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;
import ru.apmgor.orderservice.client.ProductServiceClient;
import ru.apmgor.orderservice.client.UserServiceClient;
import ru.apmgor.orderservice.dto.OrderRequestDto;
import ru.apmgor.orderservice.service.OrderService;
import ru.apmgor.productservice.dto.ProductDto;
import ru.apmgor.userservice.dto.UserDto;

@SpringBootTest
public class AllInOneIT {

    @Autowired
    private UserServiceClient userClient;

    @Autowired
    private ProductServiceClient productClient;

    @Autowired
    private OrderService service;

    @Test
    public void AllUsersPurchaseProductsTest() {
        var flux = Flux.zip(userClient.getAllUsers(), productClient.getAllProducts())
                .map(this::createOrderReqDto)
                .flatMap(service::createOrder)
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(4)
                .verifyComplete();
    }

    private Mono<OrderRequestDto> createOrderReqDto(final Tuple2<UserDto, ProductDto> tuple) {
        return Mono.fromSupplier(() -> new OrderRequestDto(tuple.getT1().id(), tuple.getT2().id())).share();
    }
}
