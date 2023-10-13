package ru.apmgor.userservice.mapper;

import org.springframework.stereotype.Component;
import ru.apmgor.userservice.dto.UserDto;
import ru.apmgor.userservice.entity.User;

@Component
public final class UserMapper implements Mapper<User, UserDto> {

    @Override
    public UserDto toDto(final User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getBalance()
        );
    }

    @Override
    public User toEntity(final UserDto dto) {
        return User.builder()
                .id(dto.id())
                .name(dto.name())
                .balance(dto.balance())
                .build();
    }
}
