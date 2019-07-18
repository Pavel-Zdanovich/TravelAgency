package com.epam.travelAgency.repository;

import com.epam.travelAgency.specification.*;

import java.util.Collection;

public interface Repository<E> {

    void add(AddSpecification<E> specification);//INSERT
    void update(UpdateSpecification<E> specification);//UPDATE
    void remove(RemoveSpecification<E> specification);//DELETE
    Collection<E> query(FindSpecification<E, Object> specification);//SELECT

}
