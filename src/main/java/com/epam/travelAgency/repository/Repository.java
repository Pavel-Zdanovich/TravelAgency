package com.epam.travelAgency.repository;

import com.epam.travelAgency.specification.AddSpecification;
import com.epam.travelAgency.specification.FindSpecification;
import com.epam.travelAgency.specification.RemoveSpecification;
import com.epam.travelAgency.specification.UpdateSpecification;

import java.util.Collection;

@org.springframework.stereotype.Repository
public interface Repository<E> {

    void add(AddSpecification<E> specification);//INSERT
    void update(UpdateSpecification<E> specification);//UPDATE
    void remove(RemoveSpecification<E> specification);//DELETE
    Collection<E> query(FindSpecification<E, ?> specification);//SELECT

}
