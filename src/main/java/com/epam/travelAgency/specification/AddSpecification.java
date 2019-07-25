package com.epam.travelAgency.specification;

import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface AddSpecification<E> extends Queryable<E>, PreparedStatementSetter {



}
