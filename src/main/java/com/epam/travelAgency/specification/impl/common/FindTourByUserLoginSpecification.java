package com.epam.travelAgency.specification.impl.common;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.entity.metamodel.Tour_;
import com.epam.travelAgency.entity.metamodel.User_;
import com.epam.travelAgency.specification.FindSpecification;
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
    public CriteriaQuery<Tour> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Tour> tourCriteriaQuery = criteriaBuilder.createQuery(Tour.class);
        CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<Tour> tourRoot = tourCriteriaQuery.from(Tour.class);
        Root<User> userRoot = userCriteriaQuery.from(User.class);
        Join<User, Tour> tours = userRoot.join(User_.TOURS, JoinType.INNER);
        ParameterExpression<String> loginParameter = criteriaBuilder.parameter(String.class, User_.LOGIN);
        userCriteriaQuery.select(userRoot.get(User_.USER_ID)).where(criteriaBuilder.equal(userRoot.get(User_.LOGIN), loginParameter));
        return tourCriteriaQuery.select(tourRoot).where(criteriaBuilder.equal(tours.get(Tour_.USERS).get(User_.LOGIN), userCriteriaQuery));
    }

}
