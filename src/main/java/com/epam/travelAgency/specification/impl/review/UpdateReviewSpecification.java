package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.specification.UpdateSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class UpdateReviewSpecification implements UpdateSpecification<Review> {

    public static final String UPDATE_REVIEW =  "UPDATE reviews SET review_id = ?, date = ?, text = ?, user_id = ?, tour_id = ?";
    @Autowired
    private Review review;

    public UpdateReviewSpecification() {
    }

    public UpdateReviewSpecification(Review review) {
        this.review = review;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, review.getReviewId());
        preparedStatement.setTimestamp(2, review.getDate());
        preparedStatement.setString(3, review.getText());
        preparedStatement.setLong(4, review.getUserId());
        preparedStatement.setLong(5 ,review.getTourId());
    }

    @Override
    public String getSQLQuery() {
        return UPDATE_REVIEW;
    }

}
