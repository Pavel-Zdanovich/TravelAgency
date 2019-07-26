package com.epam.travelAgency.specification.impl.common;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindReviewByUserSpecification implements FindSpecification<Review, User> {

    public static final String SELECT_REVIEW_BY_USER = "SELECT * FROM reviews WHERE user_id IN (SELECT user_id FROM users WHERE user_id = %d AND login = '%s' AND password = '%s')";
    @Autowired
    private User user;

    public FindReviewByUserSpecification() {}

    public FindReviewByUserSpecification(User user) {
        this.user = user;
    }

    @Override
    public void setSpecification(User user) {
        this.user = user;
    }

    @Override
    public User getSpecification() {
        return this.user;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_REVIEW_BY_USER, user.getUserId(), user.getLogin(), user.getPassword());
    }
}
