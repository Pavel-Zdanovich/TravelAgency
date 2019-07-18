package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.pool.ConnectionPool;
import com.epam.travelAgency.repository.Repository;
import com.epam.travelAgency.specification.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

@org.springframework.stereotype.Repository
public class UserRepository implements Repository<User> {

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @Override
    public void add(AddSpecification<User> specification) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(specification.getSQLQuery())) {
            int batches = specification.specified(preparedStatement);
            for (int i = 0; i < batches; i++) {
                preparedStatement.executeBatch();
            }
        } catch (SQLException e) {
            logger.error("Execute SQL query error");
        }
    }

    @Override
    public void update(UpdateSpecification<User> specification) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(specification.getSQLQuery())) {
            int batches = specification.specified(preparedStatement);
            for (int i = 0; i < batches; i++) {
                preparedStatement.executeBatch();
            }
        } catch (SQLException e) {
            logger.error("Execute SQL query error");
        }
    }

    @Override
    public void remove(RemoveSpecification<User> specification) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(specification.getSQLQuery())) {
            int batches = specification.specified(preparedStatement);
            for (int i = 0; i < batches; i++) {
                preparedStatement.executeBatch();
            }
        } catch (SQLException e) {
            logger.error("Execute SQL query error");
        }
    }

    @Override
    public Collection<User> query(FindSpecification<User, Object> specification) {
        Collection<User> selectedUsers = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(specification.getSQLQuery())) {
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                selectedUsers.add(user);
            }
        } catch (SQLException e) {
            logger.error("Execute SQL query error");
        }
        return selectedUsers;
    }

}
