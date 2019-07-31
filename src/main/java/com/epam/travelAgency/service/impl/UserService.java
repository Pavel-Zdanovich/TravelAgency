package com.epam.travelAgency.service.impl;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.repository.impl.UserRepository;
import com.epam.travelAgency.service.Service;
import com.epam.travelAgency.specification.impl.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@org.springframework.stereotype.Service
@Transactional
public class UserService implements Service<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Collection<User> findAll() {
        FindAllUsersSpecification findAllUsersSpecification = new FindAllUsersSpecification();
        return userRepository.query(findAllUsersSpecification);
    }

    @Override
    public User findById(long userId) {
        FindUserByIdSpecification findUserByIdSpecification = new FindUserByIdSpecification(userId);
        return userRepository.query(findUserByIdSpecification).iterator().next();
    }

    @Override
    public void update(User user) {
        UpdateUserSpecification updateUserSpecification = new UpdateUserSpecification(user);
        userRepository.update(updateUserSpecification);
    }

    @Override
    public void save(User user) {
        AddUserSpecification addUserSpecification = new AddUserSpecification(user);
        userRepository.add(addUserSpecification);
    }

    @Override
    public void delete(User user) {
        RemoveUserSpecification removeUserSpecification = new RemoveUserSpecification(user);
        userRepository.remove(removeUserSpecification);
    }

    @Override
    public void deleteById(long userId) {
        RemoveUserByIdSpecification removeUserByIdSpecification = new RemoveUserByIdSpecification(userId);
        userRepository.remove(removeUserByIdSpecification);
    }

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getLong("user_id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }
}
