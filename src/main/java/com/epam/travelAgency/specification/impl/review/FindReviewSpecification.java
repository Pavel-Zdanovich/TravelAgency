package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindReviewSpecification implements FindSpecification<Review, Review> {

    public static final String SELECT_REVIEW = "SELECT * FROM reviews WHERE review_id = %d AND date = '%s' AND text = '%s' AND user_id = %s AND tour_id = %s";
    @Autowired
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

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_REVIEW, review.getReviewId(), review.getDate(), review.getText(), review.getUserId(), review.getTourId());
    }

}
