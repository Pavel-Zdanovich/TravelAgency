package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.RemoveSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class RemoveUserSpecification implements RemoveSpecification<User> {

    public static final String DELETE_USER = "DELETE FROM users WHERE user_id = ? AND login = ? AND password = ?";
    @Autowired
    private User user;

    public RemoveUserSpecification() {
    }

    public RemoveUserSpecification(User user) {
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
        preparedStatement.setLong(1, user.getUserId());
        preparedStatement.setString(2, user.getLogin());
        preparedStatement.setString(3, user.getPassword());
    }

    @Override
    public String getSQLQuery() {
        return DELETE_USER;
    }

}
