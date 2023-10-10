package ru.apmgor.productservice.router;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.reactivestreams.Publisher;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.testcontainers.shaded.org.apache.commons.lang3.math.NumberUtils;
import reactor.core.publisher.Mono;
import ru.apmgor.productservice.dto.ProductDto;
import ru.apmgor.productservice.service.ProductService;

import java.net.URI;
import java.time.Duration;

import static org.springframework.web.reactive.function.server.ServerResponse.*;
import static ru.apmgor.productservice.router.RequestUtil.*;

@Component
@RequiredArgsConstructor
public final class ProductHandler {

    private final ProductService service;

    public Mono<ServerResponse> handleAll(final ServerRequest request) {
        return response(service.getAllProducts());
    }

    public Mono<ServerResponse> handleOne(final ServerRequest request) {
        return Mono.fromSupplier(() -> request.pathVariable(ID))
                .map(service::getOneProduct)
                .flatMap(this::response)
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> handleSave(final ServerRequest request) {
        return service.saveProduct(request.bodyToMono(ProductDto.class))
                .map(id -> URI.create(request.path() + "/" + id))
                .flatMap(uri -> created(uri).build());
    }

    public Mono<ServerResponse> handleUpdate(final ServerRequest request) {
        val id = request.pathVariable(ID);
        val dtoMono = request.bodyToMono(ProductDto.class);
        return service.updateProduct(id, dtoMono)
                .flatMap(dto -> noContent().build())
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> handleDelete(final ServerRequest request) {
        return Mono.fromSupplier(() -> request.pathVariable(ID))
                .flatMap(service::deleteProduct)
                .then(noContent().build())
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> handlePriceBetween(final ServerRequest request) {
        if (isValid(request, MIN) && isValid(request, MAX)) {
            return response(service.getPriceBetween(
                    getInt(request, MIN),
                    getInt(request, MAX)));
        } else {
            return badRequest().build();
        }
    }

    private int getInt(final ServerRequest request, final String param) {
        return request.queryParam(param)
                .map(Integer::parseInt)
                .get();
    }

    private boolean isValid(final ServerRequest request, final String param) {
        return request.queryParam(param)
                .filter(NumberUtils::isCreatable)
                .isPresent();
    }

    private Mono<ServerResponse> response(final Publisher<ProductDto> publisher) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .cacheControl(CacheControl.maxAge(Duration.ofSeconds(60)))
                .body(publisher, ProductDto.class);
    }
}

