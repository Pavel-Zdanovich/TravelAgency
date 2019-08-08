package com.epam.travelAgency.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public interface FindSpecification<P, S> extends Queryable<P> {

    void setSpecification(S specification);
    S getSpecification();
    CriteriaQuery<P> getCriteriaQuery(CriteriaBuilder criteriaBuilder);

}
