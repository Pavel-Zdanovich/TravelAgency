package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.specification.FindSpecification;

public class FindReviewByTourIdSpecification implements FindSpecification<Review, Long> {

    public static final String SELECT_REVIEW_BY_TOUR_ID = "SELECT * FROM reviews WHERE tour_id = %d";
    private Long tourId;

    public FindReviewByTourIdSpecification() {}

    public FindReviewByTourIdSpecification(Long tourId) {
        this.tourId = tourId;
    }

    @Override
    public void setSpecification(Long tourId) {
        this.tourId = tourId;
    }

    @Override
    public Long getSpecification() {
        return this.tourId;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_REVIEW_BY_TOUR_ID, this.tourId);
    }
}
