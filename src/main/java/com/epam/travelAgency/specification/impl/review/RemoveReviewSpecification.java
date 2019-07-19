package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.specification.RemoveSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoveReviewSpecification implements RemoveSpecification<Review> {

    public static final String DELETE_REVIEW = "DELETE FROM reviews WHERE review_id = ?, date = ?, text = ?";
    private Review review;

    public RemoveReviewSpecification(Review review) {
        this.review = review;
    }

    @Override
    public int specified(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.addBatch(DELETE_REVIEW);
        preparedStatement.setLong(1, review.getReviewId());
        preparedStatement.setTimestamp(2, review.getDate());
        preparedStatement.setString(3, review.getText());
        return 1;
    }

    @Override
    public String getSQLQuery() {
        return DELETE_REVIEW;
    }

}
