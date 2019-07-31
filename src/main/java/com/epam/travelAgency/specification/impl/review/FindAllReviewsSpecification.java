package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

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
}
