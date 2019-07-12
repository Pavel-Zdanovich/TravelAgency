package com.epam.travelAgency.specification.impl;

import com.epam.travelAgency.repository.SqlOperator;
import com.epam.travelAgency.specification.Specification;

public class PasswordSpecification implements Specification<String> {

    @Override
    public boolean specified(String parameter) {
        return false;
    }

    @Override
    public String getSqlQuery(SqlOperator sqlOperator) {
        return null;
    }

}
