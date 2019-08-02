package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.specification.FindSpecification;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

    @Override
    public CriteriaQuery<Review> toCriteriaQuery(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
        Root<Review> root = criteriaQuery.from(Review.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("review_id"), this.reviewId));
        return criteriaQuery;
    }
}
