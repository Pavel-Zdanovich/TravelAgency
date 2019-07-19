package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.specification.FindSpecification;

public class FindReviewByUserIdSpecification implements FindSpecification<Review, Long> {

    public static final String SELECT_REVIEW_BY_USER_ID = "SELECT * FROM reviews WHERE user_id = %d";
    private Long userId;

    public FindReviewByUserIdSpecification() {}

    public FindReviewByUserIdSpecification(Long userId) {
        this.userId = userId;
    }

    @Override
    public void setSpecification(Long userId) {
        this.userId = userId;
    }

    @Override
    public Long getSpecification() {
        return this.userId;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_REVIEW_BY_USER_ID, this.userId);
    }
}
