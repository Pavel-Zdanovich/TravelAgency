package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

@Component
public class FindReviewByIdSpecification implements FindSpecification<Review, Long> {

    public static final String SELECT_REVIEW_BY_ID = "SELECT * FROM reviews WHERE review_id = %d";
    private Long reviewId;

    public FindReviewByIdSpecification() {
    }

    public FindReviewByIdSpecification(Long reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public void setSpecification(Long reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public Long getSpecification() {
        return this.reviewId;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_REVIEW_BY_ID, this.reviewId);
    }
}
