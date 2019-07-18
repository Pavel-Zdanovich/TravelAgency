package com.epam.travelAgency.specification;

@FunctionalInterface
public interface SqlSpecification<P> {

    String getSQLQuery();

}
