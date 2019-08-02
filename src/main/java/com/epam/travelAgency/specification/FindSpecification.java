package com.epam.travelAgency.specification;

import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;

public interface FindSpecification<P, S> extends Queryable<P> {

    void setSpecification(S specification);
    S getSpecification();
    CriteriaQuery<P> toCriteriaQuery(Session session);

}
