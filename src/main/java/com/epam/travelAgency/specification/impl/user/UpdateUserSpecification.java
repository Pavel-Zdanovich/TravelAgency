package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.UpdateSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class UpdateUserSpecification implements UpdateSpecification<User> {

    public static final String UPDATE_USER_LOGIN = "UPDATE users SET user_id = ?, login = ?, password = ?";
    @Autowired
    private User user;

    public UpdateUserSpecification() {
    }

    public UpdateUserSpecification(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, user.getUserId());
        preparedStatement.setString(2, user.getLogin());
        preparedStatement.setString(3, user.getPassword());
    }

    @Override
    public String getSQLQuery() {
        return UPDATE_USER_LOGIN;
    }

}
