package ru.apmgor.userservice.mapper;

import org.springframework.stereotype.Component;
import ru.apmgor.userservice.dto.UserDto;
import ru.apmgor.userservice.entity.User;

@Component
public final class UserMapper implements Mapper<User, UserDto> {

    @Override
    public UserDto toDto(final User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .balance(user.getBalance())
                .build();
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
