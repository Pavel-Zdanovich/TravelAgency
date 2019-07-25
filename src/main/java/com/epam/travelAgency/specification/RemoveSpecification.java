package com.epam.travelAgency.specification;

import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface RemoveSpecification<E> extends Queryable<E>, PreparedStatementSetter {

}
