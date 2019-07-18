package com.epam.travelAgency.specification;

public interface FindSpecification<P, S> extends SqlSpecification<P> {

    void setSpecification(S specification);
    S getSpecification();

}
