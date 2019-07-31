package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.specification.RemoveSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class RemoveReviewSpecification implements RemoveSpecification<Review> {

    public static final String DELETE_REVIEW = "DELETE FROM reviews WHERE review_id = ? AND date = ? AND text = ? AND user_id = ? AND tour_id = ?";
    @Autowired
    private Review review;

    public RemoveReviewSpecification() {
    }

    public RemoveReviewSpecification(Review review) {
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
        preparedStatement.setLong(4, review.getUser().getUserId());
        preparedStatement.setLong(5 ,review.getTour().getTourId());
    }

    @Override
    public String getSQLQuery() {
        return DELETE_REVIEW;
    }

}
