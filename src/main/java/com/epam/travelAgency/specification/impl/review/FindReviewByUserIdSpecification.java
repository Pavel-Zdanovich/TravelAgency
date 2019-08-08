package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.entity.metamodel.Review_;
import com.epam.travelAgency.entity.metamodel.User_;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
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

    @Override
    public CriteriaQuery<Review> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Review> reviewCriteriaQuery = criteriaBuilder.createQuery(Review.class);
        Root<Review> reviewRoot = reviewCriteriaQuery.from(Review.class);
        return reviewCriteriaQuery.select(reviewRoot).where(criteriaBuilder.equal(reviewRoot.get(Review_.USER).get(User_.USER_ID), this.userId));
    }
}
