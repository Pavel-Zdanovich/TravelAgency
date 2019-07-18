package com.epam.travelAgency.specification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface AddSpecification<E> extends SqlSpecification<E> {

    int specified(PreparedStatement preparedStatement) throws SQLException;

}
