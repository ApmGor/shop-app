package ru.apmgor.mapper;

public interface Mapper<E, D> {
    D toDto(final E entity);
    E toEntity(final D dto);
}
