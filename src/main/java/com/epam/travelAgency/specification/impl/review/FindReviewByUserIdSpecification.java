package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.specification.FindSpecification;
import org.hibernate.Session;
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
    public CriteriaQuery<Review> toCriteriaQuery(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
        Root<Review> root = criteriaQuery.from(Review.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("user_id"), this.userId));
        return criteriaQuery;
    }
}
