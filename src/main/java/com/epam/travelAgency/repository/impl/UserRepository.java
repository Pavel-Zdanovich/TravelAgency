package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.repository.Repository;
import com.epam.travelAgency.specification.AddSpecification;
import com.epam.travelAgency.specification.FindSpecification;
import com.epam.travelAgency.specification.RemoveSpecification;
import com.epam.travelAgency.specification.UpdateSpecification;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@org.springframework.stereotype.Repository
@Transactional
public class UserRepository implements Repository<User> {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SessionFactory sessionFactory;

    public UserRepository() {
    }

    @Override
    public void add(AddSpecification<User> specification) {
        Session session = sessionFactory.openSession();
        session.merge(specification.getEntity());
        session.close();
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public void update(UpdateSpecification<User> specification) {
        Session session = sessionFactory.openSession();
        session.update(specification.getEntity());
        session.close();
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public void remove(RemoveSpecification<User> specification) {
        Session session = sessionFactory.openSession();
        session.remove(specification.getEntity());
        session.close();
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public Collection<User> query(FindSpecification<User, ? extends Object> specification) {
        Session session = sessionFactory.openSession();
        org.hibernate.query.Query<User> query = session.createQuery(specification.toCriteriaQuery(session));
        Collection<User> users = query.stream().collect(Collectors.toCollection(ArrayList::new));
        session.close();
        return users;
        //return jdbcTemplate.query(specification.getSQLQuery(), new UserService());//TODO DI
    }

}
