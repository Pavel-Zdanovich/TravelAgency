package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.repository.Repository;
import com.epam.travelAgency.repository.SqlOperator;
import com.epam.travelAgency.specification.Specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository implements Repository<User> {

    private final Map<Long, User> repository;

    public UserRepository() {
        this.repository = new HashMap<>();
    }

    @Override
    public User add(User user) {
        return repository.put(user.getUserId(), user);
    }

    @Override
    public User update(User user) {
        return repository.put(user.getUserId(), user);
    }

    @Override
    public User remove(User user) {
        return repository.remove(user.getUserId());
    }

    @Override
    public List<User> query(SqlOperator sqlOperator, Specification<User> specification) {
        List<User> users = new ArrayList<>();
        String sqlQueryString = specification.getSqlQuery(sqlOperator);//TODO solve sql-query-creating problem
        for (Map.Entry<Long, User> entry : repository.entrySet()) {
            if (specification.specified(entry.getValue())) {
                users.addAll(someMethodFromAnotherClass(sqlQueryString));
            }
        }
        return users;
    }

    public List<User> someMethodFromAnotherClass(String query) {
        return new ArrayList();
    }

}
