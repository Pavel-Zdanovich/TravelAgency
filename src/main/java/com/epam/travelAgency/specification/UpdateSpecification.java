package com.epam.travelAgency.specification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface UpdateSpecification<E> extends Queryable<E> {

    int specified(PreparedStatement preparedStatement) throws SQLException;

}
