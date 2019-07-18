package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.RemoveSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoveUserByLoginSpecification implements RemoveSpecification<User> {

    public static final String DELETE_USER_BY_LOGIN = "DELETE FROM users WHERE login = ?";
    private User user;

    public RemoveUserByLoginSpecification(User user) {
        this.user = user;
    }

    @Override
    public int specified(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.addBatch(DELETE_USER_BY_LOGIN);
        preparedStatement.setString(1, user.getLogin());
        return 1;
    }

    @Override
    public String getSQLQuery() {
        return DELETE_USER_BY_LOGIN;
    }

}
