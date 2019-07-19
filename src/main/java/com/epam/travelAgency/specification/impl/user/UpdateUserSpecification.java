package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.UpdateSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateUserSpecification implements UpdateSpecification<User> {

    public static final String UPDATE_USER_LOGIN = "UPDATE users SET user_id = ?, login = ?, password = ?";
    private User user;

    public UpdateUserSpecification(User user) {
        this.user = user;
    }

    @Override
    public int specified(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.addBatch(UPDATE_USER_LOGIN);
        preparedStatement.setLong(1, user.getUserId());
        preparedStatement.setString(2, user.getLogin());
        preparedStatement.setString(3, user.getPassword());
        return 1;
    }

    @Override
    public String getSQLQuery() {
        return UPDATE_USER_LOGIN;
    }

}
