package com.epam.travelAgency.specification.impl.common;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.FindSpecification;

public class FindTourByUserSpecification implements FindSpecification<Tour, User> {

    public static final String SELECT_TOUR_BY_USER = "SELECT * FROM tours WHERE tour_id IN (SELECT tour_id FROM users_tours WHERE user_id IN (SELECT user_id FROM users WHERE user_id = %d, login = %s, password = %s))";
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
}
