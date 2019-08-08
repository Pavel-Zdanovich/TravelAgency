package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class FindAllReviewsSpecification implements FindSpecification<Review, Object> {

    public static final String SELECT_ALL_REVIEWS = "SELECT * FROM reviews";

    @Override
    public void setSpecification(Object specification) {
        //TODO
    }

    @Override
    public Object getSpecification() {
        return null;//TODO
    }

    @Override
    public String getSQLQuery() {
        return SELECT_ALL_REVIEWS;
    }

    @Override
    public CriteriaQuery<Review> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
        Root<Review> root = criteriaQuery.from(Review.class);
        return criteriaQuery.select(root);
    }

}
