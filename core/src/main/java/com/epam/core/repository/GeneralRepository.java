package com.epam.core.repository;

import com.epam.core.util.GeneralCriterion;

import java.io.Serializable;

public interface GeneralRepository {

    <E extends Serializable> E save(E entity);

    <E extends Serializable> Iterable<E> saveAll(Iterable<E> entities);

    <E extends Serializable> Iterable<E> findBy(Class<E> entityClass, GeneralCriterion<E> generalCriterion);

    <E extends Serializable> Iterable<E> findAll(Class<E> entityClass);

    <E extends Serializable> E update(E entity);

    <E extends Serializable> Iterable updateBy(Class<E> entityClass, GeneralCriterion<E> generalCriterion);

    <E extends Serializable> Iterable<E> updateAll(Iterable<E> entities);

    <E extends Serializable> void delete(E entity);

    <E extends Serializable> void deleteBy(Class<E> entityClass, GeneralCriterion<E> generalCriterion);

    <E extends Serializable> void deleteAll(Iterable<E> entities);

}
