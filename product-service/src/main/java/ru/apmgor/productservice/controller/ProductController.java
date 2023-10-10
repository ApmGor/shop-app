package ru.apmgor.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.apmgor.productservice.dto.ProductDto;
import ru.apmgor.productservice.service.ProductService;

import java.net.URI;
import java.time.Duration;

import static org.springframework.http.ResponseEntity.*;
import static ru.apmgor.productservice.controller.RequestUtil.ID;
import static ru.apmgor.productservice.controller.RequestUtil.PRODUCT_PATH;

@RestController
@RequestMapping(PRODUCT_PATH)
@RequiredArgsConstructor
public final class ProductController {

    private final ProductService service;

    @GetMapping
    public Flux<ProductDto> allProducts() {
        return service.getAllProducts();
    }

    @GetMapping("{"+ ID +"}")
    public Mono<ResponseEntity<ProductDto>> oneProduct(@PathVariable final String id) {
        return service.getOneProduct(id)
                .map(this::response)
                .defaultIfEmpty(notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> saveProduct(@RequestBody final Mono<ProductDto> dtoMono) {
        return service.saveProduct(dtoMono)
                .map(id -> URI.create(PRODUCT_PATH + "/" + id))
                .map(uri -> created(uri).build());
    }

    @PutMapping(value = "{"+ ID +"}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> updateProduct(
            @PathVariable final String id,
            @RequestBody final Mono<ProductDto> dtoMono) {
        return service.updateProduct(id, dtoMono)
                .map(dto -> noContent().build())
                .defaultIfEmpty(notFound().build());
    }

    @DeleteMapping("{"+ ID +"}")
    public Mono<Void> deleteProduct(@PathVariable final String id) {
        return service.deleteProduct(id);
    }

    @GetMapping("/search")
    public Flux<ProductDto> allProductsWithPriceBetween(
            @RequestParam() final int max,
            @RequestParam final int min) {
        return service.getPriceBetween(min, max);
    }

    private <T> ResponseEntity<T> response(final T dto) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .cacheControl(CacheControl.maxAge(Duration.ofSeconds(60)))
                .body(dto);
    }

}
