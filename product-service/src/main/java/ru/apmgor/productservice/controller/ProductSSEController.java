package ru.apmgor.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.apmgor.productservice.dto.ProductDto;

import static ru.apmgor.productservice.controller.RequestUtil.PRODUCT_PATH;

@RestController
@RequestMapping(PRODUCT_PATH)
@RequiredArgsConstructor
public class ProductSSEController {

    private final Flux<ProductDto> flux;

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> productStream() {
        return flux;
    }
}
