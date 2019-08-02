package com.epam.travelAgency.specification.impl.common;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.FindSpecification;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class FindReviewByUserLoginSpecification implements FindSpecification<Review, String> {

    public static final String SELECT_REVIEW_BY_USER_LOGIN = "SELECT * FROM reviews WHERE user_id IN (SELECT user_id FROM users WHERE login = '%s')";
    private String login;

    public FindReviewByUserLoginSpecification() {
    }

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

    @Override
    public CriteriaQuery<Review> toCriteriaQuery(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Review> reviewCriteriaQuery = criteriaBuilder.createQuery(Review.class);
        CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<Review> reviewRoot = reviewCriteriaQuery.from(Review.class);
        Root<User> userRoot = userCriteriaQuery.from(User.class);
        userCriteriaQuery.select(userRoot.get("user_id")).where(criteriaBuilder.equal(userRoot.get("login"), this.login));
        reviewCriteriaQuery.select(reviewRoot).where(criteriaBuilder.equal(reviewRoot.get("user_id"), userCriteriaQuery));
        return reviewCriteriaQuery;
    }
}
