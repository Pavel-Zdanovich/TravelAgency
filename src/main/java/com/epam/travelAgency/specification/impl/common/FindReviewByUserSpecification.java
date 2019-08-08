package com.epam.travelAgency.specification.impl.common;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.entity.metamodel.Review_;
import com.epam.travelAgency.specification.FindSpecification;
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
    public CriteriaQuery<Review> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Review> reviewCriteriaQuery = criteriaBuilder.createQuery(Review.class);
        Root<Review> reviewRoot = reviewCriteriaQuery.from(Review.class);
        return reviewCriteriaQuery.select(reviewRoot).where(criteriaBuilder.equal(reviewRoot.get(Review_.USER), user));
    }

}
