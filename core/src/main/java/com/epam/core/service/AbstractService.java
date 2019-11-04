package com.epam.core.service;

import com.epam.core.entity.AbstractEntity;
import com.epam.core.repository.CommonRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class AbstractService<E extends AbstractEntity, ID extends Serializable, R extends CommonRepository<E, ID>>
        implements CommonService<E, ID, R> {

    protected final R repository;

    protected AbstractService(R repository) {
        this.repository = repository;
    }

    @Override
    public E save(E entity) {
        return this.repository.save(entity);
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
