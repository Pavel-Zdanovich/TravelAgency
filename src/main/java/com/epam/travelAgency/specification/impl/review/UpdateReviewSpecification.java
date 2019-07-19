package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.specification.UpdateSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateReviewSpecification implements UpdateSpecification<Review> {

    public static final String UPDATE_REVIEW =  "UPDATE reviews SET review_id = ?, date = ?, text = ?";
    private Review review;

    public UpdateReviewSpecification(Review review) {
        this.review = review;
    }

    @Override
    public int specified(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.addBatch(UPDATE_REVIEW);
        preparedStatement.setLong(1, review.getReviewId());
        preparedStatement.setTimestamp(2, review.getDate());
        preparedStatement.setString(3, review.getText());
        return 1;
    }

    @Override
    public String getSQLQuery() {
        return UPDATE_REVIEW;
    }

}
