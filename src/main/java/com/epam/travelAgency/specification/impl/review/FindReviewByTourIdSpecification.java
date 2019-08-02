package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.specification.FindSpecification;
import org.hibernate.Session;
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
    public CriteriaQuery<Review> toCriteriaQuery(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
        Root<Review> root = criteriaQuery.from(Review.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("tour_id"), this.tourId));
        return criteriaQuery;
    }
}
