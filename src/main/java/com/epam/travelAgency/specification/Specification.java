package com.epam.travelAgency.specification;

import com.epam.travelAgency.repository.SqlOperator;

public interface Specification<P> {

    boolean specified(P parameter);
    String getSqlQuery(SqlOperator sqlOperator);

}
