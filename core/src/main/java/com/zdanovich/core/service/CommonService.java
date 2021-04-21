package com.zdanovich.core.service;

import com.zdanovich.core.entity.generator.Identifiable;
import com.zdanovich.core.repository.CommonRepository;

import java.io.Serializable;
import java.util.Optional;

public interface CommonService<ID extends Serializable, E extends Identifiable<ID>, R extends CommonRepository<ID, E>> {

    E save(E entity);

    Optional<E> findById(ID entityId);

    boolean existsById(ID entityId);

    long count();

    void deleteById(ID entityId);

    void deleteAll();
}
