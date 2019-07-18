package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.RemoveSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoveUserByIdSpecification implements RemoveSpecification<User> {

    public static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
    private User user;

    public RemoveUserByIdSpecification(long userId) {
        this.user = new User();
        this.user.setUserId(userId);
    }

    @Override
    public int specified(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.addBatch(DELETE_USER_BY_ID);
        preparedStatement.setLong(1, user.getUserId());
        return 1;
    }

    @Override
    public String getSQLQuery() {
        return DELETE_USER_BY_ID;
    }

}
