package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.RemoveSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoveUserSpecification implements RemoveSpecification<User> {

    public static final String DELETE_USER = "DELETE FROM users WHERE id = ?, login = ?, password = ?";
    private User user;

    public RemoveUserSpecification(User user) {
        this.user = user;
    }

    @Override
    public int specified(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.addBatch(DELETE_USER);
        preparedStatement.setLong(1, user.getUserId());
        preparedStatement.setString(2, user.getLogin());
        preparedStatement.setString(3, user.getPassword());
        return 1;
    }

    @Override
    public String getSQLQuery() {
        return DELETE_USER;
    }

}
