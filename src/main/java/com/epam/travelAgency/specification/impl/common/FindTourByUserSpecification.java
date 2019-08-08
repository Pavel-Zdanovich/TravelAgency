package com.epam.travelAgency.specification.impl.common;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.entity.metamodel.User_;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;

@Component
public class FindTourByUserSpecification implements FindSpecification<Tour, User> {

    public static final String SELECT_TOUR_BY_USER = "SELECT * FROM tours WHERE tour_id IN (SELECT tour_id FROM users_tours WHERE user_id IN (SELECT user_id FROM users WHERE user_id = %d AND login = '%s' AND password = '%s'))";
    private User user;

    public FindTourByUserSpecification() {}

    public FindTourByUserSpecification(User user) {
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
        return String.format(SELECT_TOUR_BY_USER, user.getUserId(), user.getLogin(), user.getPassword());
    }

    @Override
    public CriteriaQuery<Tour> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Tour> tourCriteriaQuery = criteriaBuilder.createQuery(Tour.class);
        CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<Tour> tourRoot = tourCriteriaQuery.from(Tour.class);
        Root<User> userRoot = userCriteriaQuery.from(User.class);
        Join<User, Tour> tours = userRoot.join(User_.TOURS, JoinType.INNER);
        userCriteriaQuery.select(userRoot.get(User_.USER_ID)).where(criteriaBuilder.equal(userRoot.get(User_.LOGIN), user.getLogin()),
                criteriaBuilder.equal(userRoot.get(User_.PASSWORD), user.getPassword()));
        tourCriteriaQuery.select(tourRoot).where(criteriaBuilder.equal(tours.get(User_.USER_ID), userCriteriaQuery));
        return tourCriteriaQuery;
    }

}
