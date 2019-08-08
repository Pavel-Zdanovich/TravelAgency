package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.entity.metamodel.Review_;
import com.epam.travelAgency.entity.metamodel.Tour_;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
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

    @Override
    public CriteriaQuery<Review> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Review> reviewCriteriaQuery = criteriaBuilder.createQuery(Review.class);
        Root<Review> reviewRoot = reviewCriteriaQuery.from(Review.class);
        return reviewCriteriaQuery.select(reviewRoot).where(criteriaBuilder.equal(reviewRoot.get(Review_.TOUR).get(Tour_.TOUR_ID), this.tourId));
    }
}
