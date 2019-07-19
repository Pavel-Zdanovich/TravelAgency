package com.epam.travelAgency.specification.impl.common;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;

public class FindTourByUserLoginSpecification implements FindSpecification<Tour, String> {

    public static final String SELECT_TOUR_BY_USER_LOGIN = "SELECT * FROM tours WHERE tour_id IN (SELECT tour_id FROM users_tours WHERE user_id IN (SELECT user_id FROM users WHERE login = %s))";
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
}
