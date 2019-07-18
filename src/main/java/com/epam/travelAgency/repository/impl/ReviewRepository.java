package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.pool.ConnectionPool;
import com.epam.travelAgency.repository.Repository;
import com.epam.travelAgency.specification.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

@org.springframework.stereotype.Repository
public class ReviewRepository implements Repository<Review> {

    private static final Logger logger = LoggerFactory.getLogger(ReviewRepository.class);

    @Override
    public void add(AddSpecification<Review> specification) {
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(specification.getSQLQuery())) {
            int batches = specification.specified(preparedStatement);
            for (int i = 0; i < batches; i++){
                preparedStatement.executeBatch();
            }
        } catch (SQLException e) {
            logger.error("Execute SQL query error");
        }
    }

    @Override
    public void update(UpdateSpecification<Review> specification) {
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(specification.getSQLQuery())) {
            int batches = specification.specified(preparedStatement);
            for (int i = 0; i < batches; i++){
                preparedStatement.executeBatch();
            }
        } catch (SQLException e) {
            logger.error("Execute SQL query error");
        }
    }

    @Override
    public void remove(RemoveSpecification<Review> specification) {
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(specification.getSQLQuery())) {
            int batches = specification.specified(preparedStatement);
            for (int i = 0; i < batches; i++){
                preparedStatement.executeBatch();
            }
        } catch (SQLException e) {
            logger.error("Execute SQL query error");
        }
    }

    @Override
    public Collection<Review> query(FindSpecification<Review, Object> specification) {
        Collection<Review> selectedUsers = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(specification.getSQLQuery())) {
            while (resultSet.next()) {
                Review review = new Review();
                review.setReviewId(resultSet.getLong(1));
                review.setDate(resultSet.getTimestamp(2));
                review.setText(resultSet.getString(3));
            }
        } catch (SQLException e) {
            logger.error("Execute SQL query error");
        }
        return selectedUsers;
    }

}
