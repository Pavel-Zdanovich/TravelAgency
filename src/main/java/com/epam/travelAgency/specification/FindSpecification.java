package com.epam.travelAgency.specification;

public interface FindSpecification<P, S> extends Queryable<P> {

    void setSpecification(S specification);
    S getSpecification();

}
