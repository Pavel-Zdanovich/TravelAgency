package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.AddSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class AddUserSpecification implements AddSpecification<User> {

    public static final String INSERT_USER = "INSERT INTO users (user_id, login, password) VALUES (?,?,?)";
    @Autowired
    private User user;

    public AddUserSpecification() {
    }

    public AddUserSpecification(User user) {
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
        return INSERT_USER;
    }

}
