package com.zdanovich.core.service;

import com.zdanovich.core.entity.generator.Identifiable;
import com.zdanovich.core.repository.CommonRepository;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Optional;

@Transactional
public abstract class AbstractService<ID extends Serializable, E extends Identifiable<ID>, R extends CommonRepository<ID, E>>
        implements CommonService<ID, E, R> {

    protected final R repository;

    protected AbstractService(R repository) {
        this.repository = repository;
    }

    @Override
    public E save(E entity) {
        return this.repository.saveAndFlush(entity);
    }

    @Override
    public Optional<E> findById(ID entityId) {
        return this.repository.findById(entityId);
    }

    @Override
    public boolean existsById(ID entityId) {
        return this.repository.existsById(entityId);
    }

    @Override
    public long count() {
        return this.repository.count();
    }

    @Override
    public void deleteById(ID entityId) {
        this.repository.deleteById(entityId);
    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();
    }

}
