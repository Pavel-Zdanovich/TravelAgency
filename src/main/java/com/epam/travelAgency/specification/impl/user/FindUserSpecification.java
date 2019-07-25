package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindUserSpecification implements FindSpecification<User, User> {

    public static final String SELECT_USER = "SELECT * FROM users WHERE user_id = %s AND login = '%s' AND password = '%s'";
    @Autowired
    private User user;

    public FindUserSpecification() {
    }

    public FindUserSpecification(User user) {
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
        return String.format(SELECT_USER, user.getUserId(), user.getLogin(), user.getPassword());
    }
}
