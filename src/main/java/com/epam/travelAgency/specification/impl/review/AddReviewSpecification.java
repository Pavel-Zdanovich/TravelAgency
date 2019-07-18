package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.specification.AddSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddReviewSpecification implements AddSpecification<Review> {

    public static final String INSERT_REVIEW = "INSERT INTO reviews (id, date, text) VALUES (?,?,?)";
    private Review review;

    public AddReviewSpecification(Review review) {
        this.review = review;
    }

    @Override
    public int specified(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.addBatch(INSERT_REVIEW);
        preparedStatement.setLong(1, review.getReviewId());
        preparedStatement.setTimestamp(2, review.getDate());
        preparedStatement.setString(3, review.getText());
        return 1;
    }

    @Override
    public String getSQLQuery() {
        return INSERT_REVIEW;
    }

}
