package ru.apmgor.userservice.dto;

public record UserDto(
        Integer id,
        String name,
        Integer balance
) {
    public UserDto(String name, Integer balance) {
        this(null, name, balance);
    }
}
