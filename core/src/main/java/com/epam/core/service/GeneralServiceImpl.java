package com.epam.core.service;

import com.epam.core.repository.GeneralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Service(value = "hotelService")
@Profile(value = {"dev", "test"})
public class GeneralServiceImpl implements GeneralService {

    @Autowired
    private GeneralRepository generalRepositoryJPA2;

    @Override
    public <E extends Serializable> E save(E entity) {
        return generalRepositoryJPA2.save(entity);
    }

    @Override
    public <E extends Serializable> Collection<E> saveAll(Collection<E> entities) {
        Collection<E> collection = new ArrayList<>(entities.size());
        generalRepositoryJPA2.saveAll(entities).forEach(collection::add);
        return collection;
    }

    @Override
    public <E extends Serializable> Collection<E> findBy(Class<E> entityClass, Object... criteria) {
        return null;
    }

    @Override
    public <E extends Serializable> Collection<E> findAll(Class<E> entityClass) {
        Collection<E> collection = new ArrayList<>();
        generalRepositoryJPA2.findAll(entityClass).forEach(collection::add);
        return collection;
    }

    @Override
    public <E extends Serializable> E update(E entity) {
        return generalRepositoryJPA2.update(entity);
    }

    @Override
    public <E extends Serializable> Collection<E> updateBy(Class<E> entityClass, Object... criteria) {
        return null;
    }

    @Override
    public <E extends Serializable> Collection<E> updateAll(Collection<E> entities) {
        Collection<E> collection = new ArrayList<>(entities.size());
        generalRepositoryJPA2.updateAll(entities).forEach(collection::add);
        return collection;
    }

    @Override
    public <E extends Serializable> void delete(E entity) {
        generalRepositoryJPA2.delete(entity);
    }

    @Override
    public <E extends Serializable> void deleteBy(Class<E> entityClass, Object... criteria) {

    }

    @Override
    public <E extends Serializable> void deleteAll(Collection<E> entities) {
        generalRepositoryJPA2.deleteAll(entities);
    }
}
