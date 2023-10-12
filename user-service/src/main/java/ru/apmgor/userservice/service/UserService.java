package ru.apmgor.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.apmgor.userservice.dto.UserDto;
import ru.apmgor.userservice.entity.User;
import ru.apmgor.userservice.mapper.UserMapper;
import ru.apmgor.userservice.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public Flux<UserDto> getAllUsers() {
        return repository.findAll()
                .map(mapper::toDto);
    }

    public Mono<UserDto> getOneUser(final Integer id) {
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Transactional
    public Mono<Integer> saveUser(final Mono<UserDto> dtoMono) {
        return dtoMono
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(User::getId);
    }

    @Transactional
    public Mono<UserDto> updateUser(final Integer id, final Mono<UserDto> dtoMono) {
        return repository.findById(id)
                .flatMap(user -> converter(id, dtoMono))
                .flatMap(repository::save)
                .map(mapper::toDto);
    }

    @Transactional
    public Mono<Void> deleteUser(final Integer id) {
        return repository.deleteById(id);
    }

    private Mono<User> converter(final Integer id, final Mono<UserDto> dtoMono) {
        return dtoMono
                .map(mapper::toEntity)
                .map(user -> user.withId(id));
    }
}





















