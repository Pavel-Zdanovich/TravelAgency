package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.FindSpecification;

public class FindUserByLoginSpecification implements FindSpecification<User, String> {

    public static final String SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE login = %s";
    private String login;

    public FindUserByLoginSpecification() {
    }

    public FindUserByLoginSpecification(String login) {
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

    /*@Override
    public void specified(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getLogin());
    }*/

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_USER_BY_LOGIN, this.login);
    }

}
