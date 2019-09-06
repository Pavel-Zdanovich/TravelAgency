package com.epam.core.repository;

import com.epam.core.util.EntityAnalyzer;
import com.epam.core.util.GeneralCriterion;
import com.epam.core.util.GeneralCriterionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository(value = "generalRepositoryJPA2")
@Profile(value = {"dev", "test"})
public class GeneralRepositoryJPA2 implements GeneralRepository {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public <E extends Serializable> E save(E entity) {
        if (!EntityAnalyzer.isDependentEntitiesPersisted(entity)) {
            entityManager.persist(entity);
            entityManager.flush();
        } else {
            update(entity);
        }
        return entity;
    }

    @Transactional
    @Override
    public <E extends Serializable> Iterable<E> saveAll(Iterable<E> entities) {
        return StreamSupport.stream(entities.spliterator(), false).peek(entity -> {
            if (!EntityAnalyzer.isDependentEntitiesPersisted(entity)) {
                entityManager.persist(entity);
                entityManager.flush();//TODO make flush one time
            } else {
                update(entity);
            }
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public <E extends Serializable> Iterable<E> findBy(Class<E> entityClass, GeneralCriterion<E> generalCriterion) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<E> root = criteriaQuery.from(entityClass);
        GeneralCriterionConverter<E> generalCriterionConverter = new GeneralCriterionConverter<>(generalCriterion);
        criteriaQuery.select(root).where(generalCriterionConverter.toPredicates(root, criteriaQuery, criteriaBuilder));
        TypedQuery<E> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Transactional
    @Override
    public <E extends Serializable> Iterable<E> findAll(Class<E> entityClass) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<E> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        TypedQuery<E> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Transactional
    @Override
    public <E extends Serializable> E update(E entity) {
        return entityManager.merge(entity);
    }

    @Transactional
    @Override
    public <E extends Serializable> Iterable updateBy(Class<E> entityClass, GeneralCriterion<E> generalCriterion) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<E> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(entityClass);
        Root<E> root = criteriaUpdate.from(entityClass);
        GeneralCriterionConverter<E> generalCriterionConverter = new GeneralCriterionConverter<>(generalCriterion);
        criteriaUpdate.where(generalCriterionConverter.toPredicates(root, criteriaUpdate, criteriaBuilder));
        Query query = entityManager.createQuery(criteriaUpdate);//PERFORM UPDATE
        return query.getResultList();
    }

    @Transactional
    @Override
    public <E extends Serializable> Iterable<E> updateAll(Iterable<E> entities) {
        return StreamSupport.stream(entities.spliterator(), false).map(entity -> entityManager.merge(entity)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public <E extends Serializable> void delete(E entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Transactional
    @Override
    public <E extends Serializable> void deleteBy(Class<E> entityClass, GeneralCriterion<E> generalCriterion) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<E> criteriaDelete = criteriaBuilder.createCriteriaDelete(entityClass);
        Root<E> root = criteriaDelete.from(entityClass);
        GeneralCriterionConverter<E> generalCriterionConverter = new GeneralCriterionConverter<>(generalCriterion);
        criteriaDelete.where(generalCriterionConverter.toPredicates(root, criteriaDelete, criteriaBuilder));
        Query query = entityManager.createQuery(criteriaDelete);//PERFORM DELETE
    }

    @Transactional
    @Override
    public <E extends Serializable> void deleteAll(Iterable<E> entities) {
        entities.forEach(entity -> entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity)));
    }

}
