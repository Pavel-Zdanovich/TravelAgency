package com.epam.travelAgency.specification.impl.common;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.FindSpecification;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;

@Component
public class FindTourByUserLoginSpecification implements FindSpecification<Tour, String> {

    public static final String SELECT_TOUR_BY_USER_LOGIN = "SELECT * FROM tours WHERE tour_id IN (SELECT tour_id FROM users_tours WHERE user_id IN (SELECT user_id FROM users WHERE login = '%s'))";
    private String login;

    public FindTourByUserLoginSpecification() {}

    public FindTourByUserLoginSpecification(String login) {
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
        return String.format(SELECT_TOUR_BY_USER_LOGIN, this.login);
    }

    @Override
    public CriteriaQuery<Tour> toCriteriaQuery(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tour> tourCriteriaQuery = criteriaBuilder.createQuery(Tour.class);
        CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<Tour> tourRoot = tourCriteriaQuery.from(Tour.class);
        Root<User> userRoot = userCriteriaQuery.from(User.class);
        Join<User, Tour> tours = userRoot.join("tours", JoinType.INNER);
        userCriteriaQuery.select(userRoot.get("user_id")).where(criteriaBuilder.equal(userRoot.get("login"), this.login));
        tourCriteriaQuery.select(tourRoot).where(criteriaBuilder.equal(tours.get("user_id"), userCriteriaQuery));
        return tourCriteriaQuery;
    }

}
