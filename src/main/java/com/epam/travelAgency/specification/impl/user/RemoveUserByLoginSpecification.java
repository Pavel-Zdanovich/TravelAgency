package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.RemoveSpecification;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class RemoveUserByLoginSpecification implements RemoveSpecification<User> {

    public static final String DELETE_USER_BY_LOGIN = "DELETE FROM users WHERE login = ?";
    private String login;

    public RemoveUserByLoginSpecification() {
    }

    public RemoveUserByLoginSpecification(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public User getEntity() {
        User user = new User();
        user.setLogin(this.login);
        return user;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, login);
    }

    @Override
    public String getSQLQuery() {
        return DELETE_USER_BY_LOGIN;
    }

}
