package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.specification.AddSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class AddReviewSpecification implements AddSpecification<Review> {

    public static final String INSERT_REVIEW = "INSERT INTO reviews (review_id, date, text, user_id, tour_id) VALUES (?,?,?,?,?)";
    @Autowired
    private Review review;

    public AddReviewSpecification() {
    }

    public AddReviewSpecification(Review review) {
        this.review = review;
    }

    @Override
    public Review getEntity() {
        return this.review;
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
        return INSERT_REVIEW;
    }

}
