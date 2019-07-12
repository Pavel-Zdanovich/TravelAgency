package com.epam.travelAgency.repository;

import com.epam.travelAgency.specification.Specification;

import java.util.List;

public interface Repository<E> {

    E add(E entity);
    E update(E entity);
    E remove(E entity);

    List<E> query(SqlOperator sqlOperator, Specification<E> specification);

}
