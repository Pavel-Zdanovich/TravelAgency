package com.epam.travelAgency.service.impl;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.service.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService implements Service<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getLong("user_id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }
}
