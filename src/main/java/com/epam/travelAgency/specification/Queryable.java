package com.epam.travelAgency.specification;

@FunctionalInterface
public interface Queryable<P> {

    String getSQLQuery();

}
