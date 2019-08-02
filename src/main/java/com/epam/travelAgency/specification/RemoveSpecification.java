package com.epam.travelAgency.specification;

import org.springframework.jdbc.core.PreparedStatementSetter;

public interface RemoveSpecification<E> extends Queryable<E>, PreparedStatementSetter {

    E getEntity();

}
