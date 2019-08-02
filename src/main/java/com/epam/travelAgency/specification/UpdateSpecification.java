package com.epam.travelAgency.specification;

import org.springframework.jdbc.core.PreparedStatementSetter;

public interface UpdateSpecification<E> extends Queryable<E>, PreparedStatementSetter {

    E getEntity();

}
