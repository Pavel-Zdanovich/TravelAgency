package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.specification.UpdateSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class UpdateReviewSpecification implements UpdateSpecification<Review> {

    public static final String UPDATE_REVIEW =  "UPDATE reviews SET date = ?, text = ?, user_id = ?, tour_id = ? WHERE review_id = ?";
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
        preparedStatement.setTimestamp(1, review.getDate());
        preparedStatement.setString(2, review.getText());
        preparedStatement.setLong(3, review.getUser().getUserId());
        preparedStatement.setLong(4 ,review.getTour().getTourId());
        preparedStatement.setLong(5, review.getReviewId());
    }

    @Override
    public String getSQLQuery() {
        return UPDATE_REVIEW;
    }

}
