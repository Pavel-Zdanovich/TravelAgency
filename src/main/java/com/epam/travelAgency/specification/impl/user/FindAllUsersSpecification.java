package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

@Component
public class FindAllUsersSpecification implements FindSpecification<User, Object> {

    public static final String SELECT_ALL_USERS = "SELECT * FROM users";

    @Override
    public void setSpecification(Object specification) {
        //TODO
    }

    @Override
    public Object getSpecification() {
        return null;//TODO
    }

    @Override
    public String getSQLQuery() {
        return SELECT_ALL_USERS;
    }
}
