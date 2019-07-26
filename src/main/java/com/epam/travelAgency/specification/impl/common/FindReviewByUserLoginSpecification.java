package com.epam.travelAgency.specification.impl.common;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

@Component
public class FindReviewByUserLoginSpecification implements FindSpecification<Review, String> {

    public static final String SELECT_REVIEW_BY_USER_LOGIN = "SELECT * FROM reviews WHERE user_id IN (SELECT user_id FROM users WHERE login = '%s')";
    private String login;

    public FindReviewByUserLoginSpecification() {}

    public FindReviewByUserLoginSpecification(String login) {
        this.login = login;
    }

    @Override
    public void setSpecification(String login) {
        this.login = login;
    }

    @Override
    public String getSpecification() {
        return this.login;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_REVIEW_BY_USER_LOGIN, this.login);
    }

}
