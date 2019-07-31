package com.epam.travelAgency.service;

import org.springframework.jdbc.core.RowMapper;

import java.util.Collection;

public interface Service<E> extends RowMapper<E> {

    Collection<E> findAll();
    E findById(long entityId);
    void update(E entity);
    void save(E entity);
    void delete(E entity);
    void deleteById(long entityId);

}
