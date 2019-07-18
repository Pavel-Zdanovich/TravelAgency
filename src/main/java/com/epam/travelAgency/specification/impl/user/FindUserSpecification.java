package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.FindSpecification;

public class FindUserSpecification implements FindSpecification<User, User> {

    public static final String SELECT_USER = "SELECT * FROM users WHERE id = ?, login = ?, password = ?";
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

    /*@Override
    public void specified(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setLong(1, user.getUserId());
        preparedStatement.setString(2, user.getLogin());
        preparedStatement.setString(3, user.getPassword());
    }*/

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_USER, user.getUserId(), user.getLogin(), user.getPassword());
    }
}
