package com.epam.core.service;

import java.io.Serializable;
import java.util.Collection;

public interface GeneralService {

    <E extends Serializable> E save(E entity);

    <E extends Serializable> Collection<E> saveAll(Collection<E> entities);

    <E extends Serializable> Collection<E> findBy(Class<E> entityClass, Object... criteria);

    <E extends Serializable> Collection<E> findAll(Class<E> entityClass);

    <E extends Serializable> E update(E entity);

    <E extends Serializable> Collection<E> updateBy(Class<E> entityClass, Object... criteria);

    <E extends Serializable> Collection<E> updateAll(Collection<E> entities);

    <E extends Serializable> void delete(E entity);

    <E extends Serializable> void deleteBy(Class<E> entityClass, Object... criteria);

    <E extends Serializable> void deleteAll(Collection<E> entities);

}
