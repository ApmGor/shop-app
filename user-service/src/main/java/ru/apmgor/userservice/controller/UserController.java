package ru.apmgor.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.apmgor.userservice.dto.UserDto;
import ru.apmgor.userservice.service.UserService;

import java.net.URI;

import static org.springframework.http.ResponseEntity.*;
import static ru.apmgor.userservice.controller.UserUtil.ID;
import static ru.apmgor.userservice.controller.UserUtil.USER_PATH;

@RestController
@RequestMapping(USER_PATH)
@RequiredArgsConstructor
public final class UserController {

    private final UserService service;

    @GetMapping
    public Flux<UserDto> all() {
        return service.getAllUsers();
    }

    @GetMapping(value = "{" + ID + "}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<UserDto>> one(@PathVariable final Integer id) {
        return service.getOneUser(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> save(@RequestBody final Mono<UserDto> dtoMono) {
        return service.saveUser(dtoMono)
                .map(id -> URI.create(USER_PATH + "/" + id))
                .map(uri -> created(uri).build());
    }

    @PutMapping(value = "{" + ID + "}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> update(
            @PathVariable final Integer id,
            @RequestBody final Mono<UserDto> dtoMono) {
        return service.updateUser(id, dtoMono)
                .map(dto -> noContent().build())
                .defaultIfEmpty(notFound().build());
    }

    @DeleteMapping("{" + ID + "}")
    public Mono<Void> delete(@PathVariable final Integer id) {
        return service.deleteUser(id);
    }
}












