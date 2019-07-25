package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.RemoveSpecification;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class RemoveUserByIdSpecification implements RemoveSpecification<User> {

    public static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE user_id = ?";
    private Long userId;

    public RemoveUserByIdSpecification() {

    }

    public RemoveUserByIdSpecification(long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, userId);
    }

    @Override
    public String getSQLQuery() {
        return DELETE_USER_BY_ID;
    }

}
