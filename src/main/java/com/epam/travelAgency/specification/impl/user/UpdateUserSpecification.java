package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.UpdateSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class UpdateUserSpecification implements UpdateSpecification<User> {

    public static final String UPDATE_USER_LOGIN = "UPDATE users SET login = ?, password = ? WHERE user_id = ?";
    @Autowired
    private User user;

    public UpdateUserSpecification() {
    }

    public UpdateUserSpecification(User user) {
        this.user = user;
    }

    @Override
    public User getEntity() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setLong(3, user.getUserId());
    }

    @Override
    public String getSQLQuery() {
        return UPDATE_USER_LOGIN;
    }

}
