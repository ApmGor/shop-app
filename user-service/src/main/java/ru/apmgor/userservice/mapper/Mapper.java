package ru.apmgor.userservice.mapper;

public interface Mapper<E, D> {
    D toDto(final E entity);
    E toEntity(final D dto);
}
