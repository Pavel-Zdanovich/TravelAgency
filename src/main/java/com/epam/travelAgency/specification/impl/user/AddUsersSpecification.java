package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.AddSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

public class AddUsersSpecification implements AddSpecification<User> {

    public static final String INSERT_USER = "INSERT INTO users (user_id, login, password) VALUES (?,?,?)";
    private Collection<User> users;

    public AddUsersSpecification(Collection<User> users) {
        this.users = users;
    }

    @Override
    public int specified(PreparedStatement preparedStatement) throws SQLException {
        int count = 0;
        for (User user : this.users){
            preparedStatement.addBatch(INSERT_USER);
            preparedStatement.setLong(1, user.getUserId());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            count++;
        }
        return count;
    }



    @Override
    public String getSQLQuery() {
        return INSERT_USER;
    }
}
