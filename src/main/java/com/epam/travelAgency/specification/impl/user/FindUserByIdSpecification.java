package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.FindSpecification;

public class FindUserByIdSpecification implements FindSpecification<User, Long> {

    public static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = %d";
    private long userId;

    public FindUserByIdSpecification() {}

    public FindUserByIdSpecification(long userId) {
        this.userId = userId;
    }

    @Override
    public void setSpecification(Long userId) {
        this.userId = userId;
    }

    @Override
    public Long getSpecification() {
        return this.userId;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_USER_BY_ID, this.userId);
    }

}
