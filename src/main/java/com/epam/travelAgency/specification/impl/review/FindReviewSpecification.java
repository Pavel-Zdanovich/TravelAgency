package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.specification.FindSpecification;

public class FindReviewSpecification implements FindSpecification<Review, Review> {

    public static final String SELECT_REVIEW = "SELECT * FROM reviews WHERE id = %d, date = %s, text = %s";
    private Review review;

    public FindReviewSpecification() {}

    public FindReviewSpecification(Review review) {
        this.review = review;
    }

    @Override
    public void setSpecification(Review review) {
        this.review = review;
    }

    @Override
    public Review getSpecification() {
        return this.review;
    }

    /*@Override
    public void specified(PreparedStatement preparedStatement, Review review) throws SQLException {
        preparedStatement.setLong(1, review.getReviewId());
        preparedStatement.setTimestamp(2, review.getDate());
        preparedStatement.setString(3, review.getText());
    }*/

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_REVIEW, review.getReviewId(), review.getDate(), review.getText());
    }

}
