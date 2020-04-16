package com.zdanovich.core.service;

import com.zdanovich.core.entity.AbstractEntity;
import com.zdanovich.core.repository.CommonRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CommonService<E extends AbstractEntity, ID extends Serializable, R extends CommonRepository<E, ID>> {

    E save(E entity);

    Optional<E> findById(ID entityId);

    List<E> findAll();

    boolean existsById(ID entityId);

    long count();

    void deleteById(ID entityId);

    void delete(E entity);

    void deleteAll(Iterable<E> iterable);

    void deleteAll();
}
