package com.epam.travelAgency.specification.impl.common;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.FindSpecification;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

    @Override
    public CriteriaQuery<Review> toCriteriaQuery(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Review> reviewCriteriaQuery = criteriaBuilder.createQuery(Review.class);
        CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<Review> reviewRoot = reviewCriteriaQuery.from(Review.class);
        Root<User> userRoot = userCriteriaQuery.from(User.class);
        userCriteriaQuery.select(userRoot.get("user_id")).where(criteriaBuilder.equal(userRoot.get("login"), user.getLogin()),
                criteriaBuilder.equal(userRoot.get("password"), user.getPassword()));//or simply use user_id from user
        reviewCriteriaQuery.select(reviewRoot).where(criteriaBuilder.equal(reviewRoot.get("user_id"), userCriteriaQuery));
        return reviewCriteriaQuery;
    }

}
