package com.epam.travelAgency.service.impl;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.service.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewService implements Service<Review> {

    @Override
    public Review mapRow(ResultSet resultSet, int i) throws SQLException {
        Review review = new Review();
        review.setReviewId(resultSet.getLong("review_id"));
        review.setDate(resultSet.getTimestamp("date"));
        review.setText(resultSet.getString("text"));
        return review;
    }
}
