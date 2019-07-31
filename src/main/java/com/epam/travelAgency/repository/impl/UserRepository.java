package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.repository.Repository;
import com.epam.travelAgency.service.impl.UserService;
import com.epam.travelAgency.specification.AddSpecification;
import com.epam.travelAgency.specification.FindSpecification;
import com.epam.travelAgency.specification.RemoveSpecification;
import com.epam.travelAgency.specification.UpdateSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collection;

@org.springframework.stereotype.Repository
public class UserRepository implements Repository<User> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserRepository() {
    }

    /*@Autowired
    public UserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }*/

    @Override
    public void add(AddSpecification<User> specification) {
        jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public void update(UpdateSpecification<User> specification) {
        jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public void remove(RemoveSpecification<User> specification) {
        jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public Collection<User> query(FindSpecification<User, ? extends Object> specification) {
        return jdbcTemplate.query(specification.getSQLQuery(), new UserService());//TODO DI
    }

}
