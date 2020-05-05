package com.zdanovich.core.service;

import com.zdanovich.core.entity.AbstractEntity;
import com.zdanovich.core.repository.CommonRepository;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Transactional
public abstract class AbstractService<E extends AbstractEntity, ID extends Serializable, R extends CommonRepository<E, ID>>
        implements CommonService<E, ID, R> {

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
    public List<E> findAll() {
        return this.repository.findAll();
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
    public void delete(E entity) {
        this.repository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<E> iterable) {
        this.repository.deleteAll(iterable);
    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();
    }

}
